package com.zkl.l_music.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌曲评论实体
 * @author zkl
 */

@Alias(value = "CommentsEntity")
@TableName("comments")
public class CommentsEntity implements Serializable {

    @TableId
    private String id;

    private String comment;
    private UserEntity userId;
    private SongEntity songId;
    private int likes;          /*点赞数*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;          /*评论时间*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public SongEntity getSongId() {
        return songId;
    }

    public void setSongId(SongEntity songId) {
        this.songId = songId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CommentsEntity{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", userId=" + userId +
                ", songId=" + songId +
                ", likes=" + likes +
                ", time=" + time +
                '}';
    }
}
