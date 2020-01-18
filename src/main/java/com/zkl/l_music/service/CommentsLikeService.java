package com.zkl.l_music.service;

import com.zkl.l_music.entity.CommentsLikeEntity;

import java.util.List;

public interface CommentsLikeService {

    boolean addCommentsLike(CommentsLikeEntity commentsLikeEntity);

    /**
     * 点赞。状态为1
     * @param likedUserId
     * @param likedPostId
     */
    void saveLikedRedis(String likedUserId, String likedPostId,String commentId);

    /**
     * 取消点赞。将状态改变为0
     * @param likedUserId
     * @param likedPostId
     */
    void unlikeFromRedis(String likedUserId, String likedPostId,String commentId);

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    void deleteLikedFromRedis(String likedUserId, String likedPostId,String commentId);

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
//    List<LikedCountDTO> getLikedCountFromRedis();

}
