package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zkl.l_music.util.ConstantUtil;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 歌曲列表实体
 * @author zkl
 */
@Alias(value = "SongListEntity")
@TableName("song_list")
@JsonIgnoreProperties(value = {"handler"})
public class SongListEntity implements Serializable {

    @TableId
    private String id;

    private String listName;        /*列表名称*/
    private int category;           /*列表类型，1：自定义；2：我喜欢的*/
    private UserEntity userId;      /*所属用户*/
    private String introduction;    /*简介*/
    private int likeNum;               /*点赞数*/
    private String tag;             /*列表标签*/
    private int playNum;            /*播放量*/
    private String picture;         /*封面*/

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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "SongListEntity{" +
                "id='" + id + '\'' +
                ", listName='" + listName + '\'' +
                ", category=" + category +
                ", userId=" + userId +
                ", introduction='" + introduction + '\'' +
                ", likeNum=" + likeNum +
                ", tag='" + tag + '\'' +
                ", playNum=" + playNum +
                ", picture='" + picture + '\'' +
                '}';
    }
}
