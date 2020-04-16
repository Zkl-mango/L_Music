package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 推荐歌曲记录表
 * @author zhengkunli
 */
@Alias(value = "RecommentEntity")
@TableName("recomment")
@JsonIgnoreProperties(value = {"handler"})
public class RecommentEntity implements Serializable {

    @TableId
    private String id;
    private UserEntity userId;
    private String linkId;
    private double type;               /*相关度*/
    private int linkType;               /*1 歌曲，3 歌单*/

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

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public double getType() {
        return type;
    }

    public void setType(double type) {
        this.type = type;
    }

    public int getLinkType() {
        return linkType;
    }

    public void setLinkType(int linkType) {
        this.linkType = linkType;
    }

    @Override
    public String toString() {
        return "RecommentEntity{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", linkId='" + linkId + '\'' +
                ", type=" + type +
                ", linkType=" + linkType +
                '}';
    }
}
