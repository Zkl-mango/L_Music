package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.util.ConstantUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = {"handler"})
public class SongListVo implements Serializable {

    private String id;
    private String listName;        /*列表名称*/
    private String picture;         /*列表封面*/
    private String introduction;    /*简介*/
    private int likeNum;               /*点赞数*/
    private String tag;             /*列表标签*/
    private int playNum;            /*播放量*/
    private int category;           /*列表类型，1：自定义；2：我喜欢的*/
    private int songNum;            /*歌曲数量*/
    private UserEntity userId;      /*所属用户*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;              /*时间*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSongNum() {
        return songNum;
    }

    public void setSongNum(int songNum) {
        this.songNum = songNum;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SongListVo{" +
                "id='" + id + '\'' +
                ", listName='" + listName + '\'' +
                ", picture='" + picture + '\'' +
                ", introduction='" + introduction + '\'' +
                ", likeNum=" + likeNum +
                ", tag='" + tag + '\'' +
                ", playNum=" + playNum +
                ", category=" + category +
                ", songNum=" + songNum +
                ", userId=" + userId +
                ", time=" + time +
                '}';
    }
}
