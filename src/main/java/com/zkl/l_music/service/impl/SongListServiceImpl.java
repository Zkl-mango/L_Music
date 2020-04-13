package com.zkl.l_music.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.dao.LikeListDao;
import com.zkl.l_music.dao.SongListDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.LikeListEntity;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.HandleAvatarUtil;
import com.zkl.l_music.util.PageUtils;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SongListServiceImpl implements SongListService {

    @Resource
    SongListDao songListDao;
    @Resource
    UserDao userDao;
    @Resource
    LikeListDao likeListDao;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    SongDetailsService songDetailsService;

    //添加歌单
    @Override
    @Transactional
    public String addSongList(String songListName, String id,int category) {
        SongListEntity songListEntity = new SongListEntity();
        songListEntity.setListName(songListName);
        songListEntity.setId(uuidGenerator.generateUUID());
        songListEntity.setUserId(userDao.selectById(id));
        songListEntity.setPicture(ConstantUtil.listPic);
        songListEntity.setCategory(category);
        int res = songListDao.insert(songListEntity);
        if(res == 1) {
            return songListEntity.getId();
        }
        return null;
    }

    //修改歌单信息
    @Override
    @Transactional
    public boolean updateSongList(SongListEntity songListEntity) {
        SongListEntity songList = songListDao.selectById(songListEntity.getId());
        if(songList==null) {
            return false;
        }
        if(songListEntity.getPlayNum() == 0) {
            songListEntity.setPlayNum(songList.getPlayNum());
        }
        if(songList.getLikeNum() == 0) {
            songListEntity.setLikeNum(songList.getLikeNum());
        }
        songListEntity.setUserId(null);
        songListEntity.setCategory(songList.getCategory());
        int res = songListDao.updateById(songListEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateSongListPicture(String id, MultipartFile file) {
        log.info("开始更新歌曲封面-----");
        String picture = HandleAvatarUtil.save(file,2);
        SongListEntity songListEntity = songListDao.selectById(id);
        if(songListEntity == null) {
            return false;
        }
        SongListEntity songList = new SongListEntity();
        songList.setPicture(picture);
        songList.setId(songListEntity.getId());
        songList.setPlayNum(songListEntity.getPlayNum());
        songList.setLikeNum(songListEntity.getLikeNum());
        log.info("更新歌曲封面结束-----");
        songListDao.updateById(songList);
        return true;
    }

    @Override
    public boolean updateSongListNum(String id, int flag, int type,String userId) {
        SongListEntity songListEntity = songListDao.selectById(id);
        if(songListEntity == null) {
            return false;
        }
        //更新播放量
        if(type == 1) {
            songListEntity.setPlayNum(songListEntity.getPlayNum()+flag);
        }
        //更新点赞数
        else if(type == 2) {
            songListEntity.setLikeNum(songListEntity.getPlayNum()+flag);
            LikeListEntity likeListEntity = new LikeListEntity();
            if(flag == 1) {
                likeListEntity.setId(uuidGenerator.generateUUID());
                likeListEntity.setLinkId(id);
                likeListEntity.setTime(new Date());
                likeListEntity.setType(ConstantUtil.listType);
                likeListEntity.setUserId(userDao.selectById(userId));
                likeListDao.insert(likeListEntity);
            } else if (flag == -1) {
                likeListEntity = likeListDao.selectLikeListByUserAndLike(userId,id);
                likeListDao.deleteById(likeListEntity.getId());
            }
        }
        songListEntity.setUserId(null);
        songListDao.updateById(songListEntity);
        return true;
    }

    //删除歌单
    @Override
    public boolean deleteSongList(String id) {
        int res = songListDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }


    //通过歌单id获取歌单详情
    @Override
    public SongListVo getSongListById(String id,String userId) {
        SongListEntity songListEntity = songListDao.selectById(id);
        if(songListEntity == null) {
            return null;
        }
        SongListVo songListVo = new SongListVo();
        BeanUtils.copyProperties(songListEntity,songListVo);
        songListVo.getUserId().setAvatar(ConstantUtil.avatardownloadPath+songListEntity.getUserId().getAvatar());
        if(StringUtils.isBlank(userId)) {
            songListVo.setSongNum(0);
        } else {
            LikeListEntity likeListEntity = likeListDao.selectLikeListByUserAndLike(userId,id);
            if(likeListEntity==null) {
                songListVo.setSongNum(0);
            } else {
                songListVo.setSongNum(1);
            }
        }
        return songListVo;
    }

    //获取用户歌单
    @Override
    public List<SongListVo> getSongListByUser(String userId,int category) {
        List<SongListEntity> songList =  songListDao.selectSongListByUser(userId,category);
        List<SongListVo> songListVos = new ArrayList<>();
        for(int i=0;i<songList.size();i++) {
            SongListVo songListVo = new SongListVo();
            BeanUtils.copyProperties(songList.get(i),songListVo);
            songListVo.setSongNum(songDetailsService.countSongDetailsByList(songList.get(i).getId()));
            songListVos.add(songListVo);
        }
        return songListVos;
    }

    @Override
    public List<SongListVo> getLikeSongList() {
        List<SongListEntity> list = songListDao.selectLikeSongList();
        List<SongListVo> songListVos = new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            SongListVo songListVo = new SongListVo();
            BeanUtils.copyProperties(list.get(i),songListVo);
            songListVos.add(songListVo);
        }
        return songListVos;
    }

    @Override
    public List<SongListVo> getHotSongList() {
        List<SongListEntity> list = songListDao.selectPlaySongList();
        List<SongListVo> songListVos = new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            SongListVo songListVo = new SongListVo();
            BeanUtils.copyProperties(list.get(i),songListVo);
            songListVos.add(songListVo);
        }
        return songListVos;
    }

    @Override
    public PageInfoVo getSongListByTag(PageBo pageBo, String tag) {
        Page page = new Page(pageBo.getPage(),pageBo.getSize());
        if(tag.equals("推荐")) {
            tag = null;
        }
        IPage iPage = songListDao.selectSongListByTag(page,tag);
        List<SongListVo> songListVos = new ArrayList<>();
        List<SongListEntity> songlist = iPage.getRecords();
        for(int i=0;i<songlist.size();i++) {
            SongListVo songListVo = new SongListVo();
            BeanUtils.copyProperties(songlist.get(i),songListVo);
            songListVos.add(songListVo);
        }
        iPage.setRecords(songListVos);
        return PageUtils.generatePageVo(iPage);
    }
}
