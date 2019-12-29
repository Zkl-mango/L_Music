package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 播放列表实体
 * @author zkl
 */

@Alias(value = "PlayListEntity")
@TableName("play_list")
public class PlayListEntity implements Serializable {

    @TableId
    private String id;

    private UserEntity userId;      /*用户id*/
    private SongEntity songId;      /*歌曲id*/
    private int isPlay;             /*是否正在播放*/

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

    public SongEntity getSongId() {
        return songId;
    }

    public void setSongId(SongEntity songId) {
        this.songId = songId;
    }

    public int getIsPlay() {
        return isPlay;
    }

    public void setIsPlay(int isPlay) {
        this.isPlay = isPlay;
    }

    @Override
    public String toString() {
        return "PlayListEntity{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", songId=" + songId +
                ", isPlay=" + isPlay +
                '}';
    }
}
