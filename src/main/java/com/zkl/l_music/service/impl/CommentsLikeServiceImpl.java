package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.CommentsDao;
import com.zkl.l_music.dao.CommentsLikeDao;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.CommentsEntity;
import com.zkl.l_music.entity.CommentsLikeEntity;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.service.CommentsLikeService;
import com.zkl.l_music.util.LikedStatusEnum;
import com.zkl.l_music.util.RedisKeyUtils;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.CommentLikesVo;
import com.zkl.l_music.vo.CommentsDetailVo;
import com.zkl.l_music.vo.CommentsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommentsLikeServiceImpl implements CommentsLikeService {

    @Resource
    CommentsLikeDao commentsLikeDao;
    @Resource
    CommentsDao commentsDao;
    @Resource
    UserDao userDao;
    @Resource
    SingerDao singerDao;
    @Resource
    UUIDGenerator uuidGenerator;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean addCommentsLike(CommentsLikeEntity commentsLikeEntity) {
        commentsLikeEntity.setId(uuidGenerator.generateUUID());
        int res = commentsLikeDao.insert(commentsLikeEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void saveLikedRedis(String likedUserId,String commentId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId,commentId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(String likedUserId, String commentId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId,commentId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedUserId,String commentId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, commentId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key);
    }

    @Override
    public void incrementLikedCount(String commentId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_COMMENT_LIKED_COUNT, commentId, 1);
    }

    @Override
    public void decrementLikedCount(String commentId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_COMMENT_LIKED_COUNT, commentId, -1);
    }

    @Override
    public List<CommentsLikeEntity> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, ScanOptions.NONE);
        List<CommentsLikeEntity> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String commentId = split[1];
            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
            CommentsLikeEntity commentsLikeEntity = new CommentsLikeEntity();
            commentsLikeEntity.setCommentId(commentsDao.selectById(commentId));
            commentsLikeEntity.setUserId(userDao.selectById(likedUserId));
            commentsLikeEntity.setStatus(value);
            list.add(commentsLikeEntity);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key);
        }
        return list;
    }

    @Override
    public List<CommentsEntity> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_COMMENT_LIKED_COUNT, ScanOptions.NONE);
        List<CommentsEntity> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            CommentsEntity commentsEntity = new CommentsEntity();
            commentsEntity.setId(key);
            commentsEntity.setLikes((Integer)map.getValue());
            list.add(commentsEntity);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_COMMENT_LIKED_COUNT, key);
        }
        return list;
    }

    @Override
    @Transactional
    public void getLikedsFromRedisToDB() {
        //更新点赞状态
        List<CommentsLikeEntity> list = this.getLikedDataFromRedis();
        for(int i=0;i<list.size();i++) {
            CommentsLikeEntity commentsLikeEntity = list.get(i);
            CommentsLikeEntity commentsLike = commentsLikeDao.selectCommentsLike
                    (commentsLikeEntity.getUserId().getId(),commentsLikeEntity.getCommentId().getId());
            if(commentsLike==null) {
                addCommentsLike(commentsLikeEntity);
            } else {
                commentsLike.setStatus(commentsLikeEntity.getStatus());
                commentsLikeDao.updateById(commentsLike);
            }
        }
    }

    @Override
    public CommentsDetailVo getCommentByUser(String userId, CommentsEntity commentsEntity) {
        CommentsLikeEntity commentsLike = commentsLikeDao.selectCommentsLike(userId,commentsEntity.getId());
        if(commentsLike==null) {
            return null;
        }
        if(commentsLike.getStatus()==0) {
            return null;
        }
        CommentsDetailVo commentsDetailVo = new CommentsDetailVo();
        BeanUtils.copyProperties(commentsDetailVo,commentsEntity);
        //判断评论是否被用户点赞过
        commentsDetailVo.setIsUser(1);
        return commentsDetailVo;
    }

    @Override
    public List<CommentLikesVo> getCommentLikeByUser(String userId) {
        List<CommentLikesVo> list = new ArrayList<>();
        //从redis获取点赞列表
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String commentId = split[1];
            Integer value = (Integer) entry.getValue();
            if(likedUserId.equals(userId) && value == 1) {
                //组装成 UserLike 对象
                CommentLikesVo commentLikesVo = new CommentLikesVo();
                commentLikesVo.setId(commentId);
                CommentsEntity commentsEntity = commentsDao.selectById(commentId);
                commentLikesVo.setContent(commentsEntity.getComment());
                commentLikesVo.setNum(commentsEntity.getLikes());
                commentLikesVo.setUsername(commentsEntity.getUserId().getName());
                SingerEntity singerEntity = singerDao.selectById(commentsEntity.getSongId().getSingerId());
                String name = singerEntity.getSinger()+"-"+commentsEntity.getSongId().getName();
                commentLikesVo.setSongname(name);
                commentLikesVo.setIscancle(false);
                list.add(commentLikesVo);
            }
        }
        //数据库中获取点赞列表
        List<CommentsLikeEntity> commentsLikeEntities = commentsLikeDao.selectCommentsIsLike(userId);
        for(int i=0;i<commentsLikeEntities.size();i++) {
            CommentsEntity commentsEntity = commentsLikeEntities.get(i).getCommentId();
            CommentLikesVo commentLikesVo = new CommentLikesVo();
            commentLikesVo.setId(commentsEntity.getId());
            commentLikesVo.setContent(commentsEntity.getComment());
            commentLikesVo.setNum(commentsEntity.getLikes());
            commentLikesVo.setUsername(commentsEntity.getUserId().getName());
            SingerEntity singerEntity = singerDao.selectById(commentsEntity.getSongId().getSingerId());
            String name = singerEntity.getSinger()+"-"+commentsEntity.getSongId().getName();
            commentLikesVo.setSongname(name);
            commentLikesVo.setIscancle(false);
            list.add(commentLikesVo);
        }
        return list;
    }
}
