package com.zkl.l_music.util;

import com.zkl.l_music.dao.*;
import com.zkl.l_music.entity.*;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
@Component("RecommentCF")
public class RecommentCF {

    @Resource
    UserDao userDao;
    @Resource
    SongListDao songListDao;
    @Resource
    SongDetailsDao songDetailsDao;
    @Resource
    LikeListDao likeListDao;
    @Resource
    SongDao songDao;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    RecommentDao recommentDao;

    public List<SongEntity> getUserLikeSongs(String userId){
        List<SongEntity> songs = new ArrayList<>();
        //计算用户个人歌单中的歌曲
        List<SongListEntity> songLists = songListDao.selectSongListByUser(userId,1);
        List<SongListEntity> songlikeLists = songListDao.selectSongListByUser(userId,2);
        songLists.add(songlikeLists.get(0));
        for(int i=0;i<songLists.size();i++) {
            List<SongDetailsEntity> songDetailsEntities = songDetailsDao.selectSongDetailsByListId(songLists.get(i).getId());
            for(int j=0;j<songDetailsEntities.size();j++) {
                songs.add(songDetailsEntities.get(j).getSongId());
            }
        }
        //计算用户收藏的专辑和歌单中的歌曲
        List<LikeListEntity> likeAlbumLists = likeListDao.selectLikeListByType(userId,ConstantUtil.albumType);
        for(int i=0;i<likeAlbumLists.size();i++) {
            List<SongEntity> songEntities = songDao.selectSongsByAlbum(likeAlbumLists.get(i).getLinkId());
            for(int j=0;j<songEntities.size();j++) {
                songs.add(songEntities.get(j));
            }
        }
        List<LikeListEntity> likeSongLists = likeListDao.selectLikeListByType(userId,ConstantUtil.listType);
        for(int i=0;i<likeSongLists.size();i++) {
            List<SongDetailsEntity> songDetailsEntities = songDetailsDao.selectSongDetailsByListId(likeSongLists.get(i).getLinkId());
            for(int j=0;j<songDetailsEntities.size();j++) {
                songs.add(songDetailsEntities.get(j).getSongId());
            }
        }
        songs = new ArrayList<>(new HashSet<>(songs));
        return songs;
    }

    public void recommentUser() {
        //用户总量
        int N = userDao.countUser()-1;
        List<UserEntity> userList = userDao.selectAllUser();
        int[][] sparseMatrix = new int[N][N];//建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<String, Integer> userItemLength = new HashMap<>();//存储每一个用户收藏的歌曲总数总数  eg: A 3
        Map<String, Set<String>> itemUserCollection = new HashMap<>();//建立歌曲到用户的倒排表 eg: a A B
        Set<String> items = new HashSet<>();//辅助存储歌曲集合
        Map<String, Set<String>> itemUserCatCollection = new HashMap<>();//建立歌曲类型到用户的倒排表 eg: a A B
        Set<String> itemcats = new HashSet<>();//辅助存储类型集合
        Map<String, Integer> userID = new HashMap<>();//辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();//辅助存储每一个ID对应的用户映射
        for(int i = 0; i < N ; i++){//依次处理N个用户
            String userId = userList.get(i).getId();
            List<SongEntity> songs = getUserLikeSongs(userId);
            int length = songs.size();
            userItemLength.put(userId, length);//eg: A 3
            userID.put(userId, i);//用户ID与稀疏矩阵建立对应关系
            idUser.put(i, userId);
            //建立歌曲--用户倒排表,类型--用户倒排表
            for(int j = 0; j < length; j ++){
                if(items.contains(songs.get(j).getId())){//如果已经包含歌曲id--用户映射，直接添加对应的用户
                    itemUserCollection.get(songs.get(j).getId()).add(userId);
                }else{//否则创建对应歌曲--用户集合映射
                    items.add(songs.get(j).getId());
                    itemUserCollection.put(songs.get(j).getId(), new HashSet<String>());//创建歌曲--用户倒排关系
                    itemUserCollection.get(songs.get(j).getId()).add(userId);
                }

                if(itemcats.contains(songs.get(j).getCategory())){//如果已经包含类型--用户映射，直接添加对应的用户
                    itemUserCatCollection.get(songs.get(j).getCategory()).add(userId);
                }else{//否则创建对应歌曲--用户集合映射
                    itemcats.add(songs.get(j).getCategory());
                    itemUserCatCollection.put(songs.get(j).getCategory(), new HashSet<String>());//创建类型--用户倒排关系
                    itemUserCatCollection.get(songs.get(j).getCategory()).add(userId);
                }
            }
        }
        //计算相似度矩阵【稀疏】
        Set<Entry<String, Set<String>>> entrySet = new HashSet<Entry<String, Set<String>>>();
        entrySet.addAll(itemUserCollection.entrySet());
        entrySet.retainAll(itemUserCatCollection.entrySet());
        Iterator<Entry<String, Set<String>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {
                for (String user_v : commonUsers) {
                    if(user_u.equals(user_v)){
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;//计算用户u与用户v都有正反馈的音乐总数
                }
            }
        }
        //计算每个用户的听歌推荐
        for(int i = 0; i < N ; i++){
            List<RecommentEntity> songLists = new ArrayList<>();
            List<RecommentEntity> songIds = new ArrayList<>();
            String recommendUser = userList.get(i).getId();
            //计算用户之间的相似度【余弦相似性】
            int recommendUserId = userID.get(recommendUser);
            List<RecommentEntity> userLists = new ArrayList<>();
            for (int j = 0;j < sparseMatrix.length; j++) {
                if (j != recommendUserId) {
                    double itemRecommendDegree = 0.0;
                    if (Math.sqrt(userItemLength.get(idUser.get(recommendUserId)) * userItemLength.get(idUser.get(j))) == 0.0) {
                        itemRecommendDegree = 0.0;
                    } else {
                        itemRecommendDegree = sparseMatrix[recommendUserId][j] / Math.sqrt(userItemLength.get(idUser.get(recommendUserId)) * userItemLength.get(idUser.get(j)));
                    }
//                    log.info(idUser.get(recommendUserId) + "--" + idUser.get(j) + "相似度:" + itemRecommendDegree);
                    RecommentEntity recommentEntity = new RecommentEntity();
                    recommentEntity.setType(itemRecommendDegree);
                    recommentEntity.setUserId(userDao.selectById(recommendUser));
                    userLists.add(recommentEntity);
                }
            }
            //根据推荐度排序
            Collections.sort(userLists, new Comparator<RecommentEntity>(){
                @Override
                public int compare(RecommentEntity o1, RecommentEntity o2) {
                    if(o1.getType() == o2.getType()) {
                        return 0;
                    }
                    if(o1.getType() > o2.getType()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
            for(int z=0;z<userLists.size();z++) {
                if(songLists.size()==3) {
                    songLists = new ArrayList<>();
                    break;
                }
                int size=3;
                List<SongListEntity> songListVos = songListDao.selectSongListByUser(userLists.get(z).getUserId().getId(),1);
                if(songListVos.size()<=3) {
                    size = songListVos.size();
                } else {
                    size = 3;
                }
                for(int y = 0;y<size;y++) {
                    RecommentEntity recommentEntity = new RecommentEntity();
                    recommentEntity.setId(uuidGenerator.generateUUID());
                    recommentEntity.setType(userLists.get(z).getType());
                    recommentEntity.setLinkId(songListVos.get(y).getId());
                    recommentEntity.setLinkType(ConstantUtil.listType);
                    recommentEntity.setUserId(userDao.selectById(recommendUser));
                    recommentDao.insert(recommentEntity);
                    songLists.add(recommentEntity);
                }
            }
            log.info("歌单推荐完成");
            //计算指定用户recommendUser的听歌推荐度
            for(String item: items){ //遍历每一首歌曲
                Set<String> users = itemUserCollection.get(item);//得到当前歌曲的所有用户集合
                if(!users.contains(recommendUser)){//如果被推荐用户没有听过这首歌，则进行推荐度计算
                    //计算指定用户recommendUser的听歌种类
                    for(String itemcat: itemcats) { //遍历每一个种类
                        Set<String> useritemcats = itemUserCatCollection.get(itemcat);//得到当前种类的所有用户集合
                        if(!useritemcats.contains(recommendUser)) {//如果被推荐用户种类和这个歌不匹配，则跳过
                            continue;
                        } else {
                            SongEntity songEntity = songDao.selectById(item);
                            if(!songEntity.getCategory().contains(itemcat)){
                                continue;
                            } else {
                                double itemRecommendDegree = 0.0;
                                for(String user: users){
                                    if(Math.sqrt(userItemLength.get(recommendUser)*userItemLength.get(user)) == 0.0) {
                                        itemRecommendDegree = 0.0;
                                    } else {
                                        itemRecommendDegree += sparseMatrix[userID.get(recommendUser)][userID.get(user)] / Math.sqrt(userItemLength.get(recommendUser) * userItemLength.get(user));//推荐度计算
                                    }
                                }
//                                log.info("The song "+item+" for "+recommendUser +"'s recommended degree:"+itemRecommendDegree);
                                RecommentEntity recommentEntity = new RecommentEntity();
                                recommentEntity.setId(uuidGenerator.generateUUID());
                                recommentEntity.setType(itemRecommendDegree);
                                recommentEntity.setLinkId(item);
                                recommentEntity.setLinkType(ConstantUtil.songType);
                                recommentEntity.setUserId(userDao.selectById(recommendUser));
                                songIds.add(recommentEntity);
                            }
                        }
                    }
                }
            }
            //根据推荐度排序
            Collections.sort(songIds, new Comparator<RecommentEntity>(){
                @Override
                public int compare(RecommentEntity o1, RecommentEntity o2) {
                    if(o1.getType() == o2.getType()) {
                        return 0;
                    }
                    if(o1.getType() > o2.getType()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
            //添加入数据库
            int size = songIds.size();
            if(songIds.size()>10) {
                size = 10;
            }
            if(size == 0) {
                List<SongEntity> list = songDao.selectSongsByRecomment();
                for(int g=0;g<list.size();i++) {
                    RecommentEntity recommentEntity = new RecommentEntity();
                    recommentEntity.setLinkId(list.get(g).getId());
                    recommentEntity.setLinkType(ConstantUtil.songType);
                    recommentEntity.setType(1.0);
                    recommentEntity.setId(uuidGenerator.generateUUID());
                    recommentEntity.setUserId(userDao.selectById(recommendUser));
                    recommentDao.insert(recommentEntity);
                }
            } else {
                for(int k=0;k<size;k++) {
                    recommentDao.insert(songIds.get(k));
                }
            }
            log.info("歌曲推荐完成");
        }
    }

}
