package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 推荐歌曲记录表
 * @author zhengkunli
 */
@Alias(value = "RecommentEntity")
@TableName("recomment")
public class RecommentEntity implements Serializable {

    @TableId
    private String id;
    private UserEntity userEntity;
    private SongEntity songEntity;
    private int type;               /*今日是否推荐，1：是，0：否*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public SongEntity getSongEntity() {
        return songEntity;
    }

    public void setSongEntity(SongEntity songEntity) {
        this.songEntity = songEntity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RecommentEntity{" +
                "id='" + id + '\'' +
                ", userEntity=" + userEntity +
                ", songEntity=" + songEntity +
                ", type=" + type +
                '}';
    }
}
