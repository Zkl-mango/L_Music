package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.CommentsDao;
import com.zkl.l_music.dao.CommentsLikeDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.CommentsLikeEntity;
import com.zkl.l_music.service.CommentsLikeService;
import com.zkl.l_music.util.LikedStatusEnum;
import com.zkl.l_music.util.RedisKeyUtils;
import com.zkl.l_music.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    UUIDGenerator uuidGenerator;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean addCommentsLike(CommentsLikeEntity commentsLikeEntity) {
        int res = commentsLikeDao.insert(commentsLikeEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void saveLikedRedis(String likedUserId, String likedPostId,String commentId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId,commentId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId,String commentId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId,commentId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId,String commentId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId,commentId);
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
            String likedPostId = split[1];
            String commentId = split[2];
            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
            CommentsLikeEntity commentsLikeEntity = new CommentsLikeEntity();
            commentsLikeEntity.setCommentId(commentsDao.selectById(commentId));
            commentsLikeEntity.setPostId(userDao.selectById(likedPostId));
            commentsLikeEntity.setUserId(userDao.selectById(likedUserId));
            commentsLikeEntity.setStatus(value);
            commentsLikeEntity.setId(uuidGenerator.generateUUID());
            list.add(commentsLikeEntity);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key);
        }
        return list;
    }
}
