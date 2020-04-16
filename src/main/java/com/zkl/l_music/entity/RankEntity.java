package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 榜单排行实体
 * @author zhengkunli
 */
@Alias(value = "RankEntity")
@TableName("rank")
@JsonIgnoreProperties(value = {"handler"})
public class RankEntity implements Serializable {
    @TableId
    private String id;
    private String rankName;    /*榜单名称*/
    private int playNum;        /*榜单播放量*/
    private String picture;
    private int recomment;      /*是否推荐*/
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getRecomment() {
        return recomment;
    }

    public void setRecomment(int recomment) {
        this.recomment = recomment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RankEntity{" +
                "id='" + id + '\'' +
                ", rankName='" + rankName + '\'' +
                ", playNum=" + playNum +
                ", picture='" + picture + '\'' +
                ", recomment=" + recomment +
                ", type=" + type +
                '}';
    }
}
