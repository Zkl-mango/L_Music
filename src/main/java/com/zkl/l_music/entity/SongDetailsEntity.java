package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌曲列表详情实体
 * @author zkl
 */
@Alias(value = "SongDetailsEntity")
@TableName("song_details")
public class SongDetailsEntity implements Serializable {

    @TableId
    private String id;

    private SongEntity songId;          /*歌曲id*/
    private SongListEntity songList;    /*所属列表id*/

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createAt;              /*添加的时间*/
    private int deleted;                /*是否删除，1：删除；0：未删除*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SongEntity getSongId() {
        return songId;
    }

    public void setSongId(SongEntity songId) {
        this.songId = songId;
    }

    public SongListEntity getSongList() {
        return songList;
    }

    public void setSongList(SongListEntity songList) {
        this.songList = songList;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "SongDetailsEntity{" +
                "id='" + id + '\'' +
                ", songId=" + songId +
                ", songList=" + songList +
                ", createAt=" + createAt +
                ", deleted=" + deleted +
                '}';
    }
}
