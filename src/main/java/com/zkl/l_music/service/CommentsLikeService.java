package com.zkl.l_music.service;

import com.zkl.l_music.entity.CommentsEntity;
import com.zkl.l_music.entity.CommentsLikeEntity;
import com.zkl.l_music.vo.CommentsDetailVo;

import java.util.List;

public interface CommentsLikeService {

    boolean addCommentsLike(CommentsLikeEntity commentsLikeEntity);

    /**
     * 点赞。状态为1
     * @param likedUserId
     * @param commentId
     */
    void saveLikedRedis(String likedUserId, String commentId);

    /**
     * 取消点赞。将状态改变为0
     * @param likedUserId
     * @param commentId
     */
    void unlikeFromRedis(String likedUserId, String commentId);

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param commentId
     */
    void deleteLikedFromRedis(String likedUserId, String commentId);

    /**
     * 该评论的点赞数加1
     * @param commentId
     */
    void incrementLikedCount(String commentId);

    /**
     * 该评论的点赞数减1
     * @param commentId
     */
    void decrementLikedCount(String commentId);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<CommentsLikeEntity> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    List<CommentsEntity> getLikedCountFromRedis();

    /**
     * 将redis中的点赞数据存入数据库
     */
    void getLikedsFromRedisToDB() ;

    /**
     * 查看用户是否点赞
     * @param userId
     * @return
     */
    CommentsDetailVo getCommentByUser(String userId, CommentsEntity commentsEntity);
}
