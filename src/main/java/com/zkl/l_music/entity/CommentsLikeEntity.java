package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 *  评论点赞记录实体
 * @author zkl
 */
@Alias(value = "CommentsLikeEntity")
@TableName("comments_like")
public class CommentsLikeEntity implements Serializable {

    @TableId
    private String id;

    private UserEntity userId;
    private CommentsEntity commentId;
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public CommentsEntity getCommentId() {
        return commentId;
    }

    public void setCommentId(CommentsEntity commentId) {
        this.commentId = commentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CommentsLikeEntity{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", commentId=" + commentId +
                ", status=" + status +
                '}';
    }
}
