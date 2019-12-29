package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 *  播放历史列表实体
 * @author zkl
 */

@Alias(value = "HistoryListEntity")
@TableName("history_list")
public class HistoryListEntity implements Serializable {

    @TableId
    private String id;
    private SongEntity songId;
    private UserEntity userId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;              /*播放时间*/

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

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "HistoryListEntity{" +
                "id='" + id + '\'' +
                ", songId=" + songId +
                ", userId=" + userId +
                ", time=" + time +
                '}';
    }
}
