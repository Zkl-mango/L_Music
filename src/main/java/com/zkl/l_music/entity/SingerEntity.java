package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 歌手信息实体
 * @author zkl
 */
@Alias(value = "SingerEntity")
@TableName("singer")
public class SingerEntity implements Serializable {

    @TableId
    private String id;

    private String singer;
    private String englishName;
    private String sex;
    private String picture;         /*个人封面图片*/
    private int fans;               /*粉丝数*/
    private int songs;              /*歌曲数*/
    private int albums;             /*专辑数*/
    private int category;           /*类别*/
    private String about;           /*歌手简介*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getSongs() {
        return songs;
    }

    public void setSongs(int songs) {
        this.songs = songs;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "SingerEntity{" +
                "id='" + id + '\'' +
                ", singer='" + singer + '\'' +
                ", englishName='" + englishName + '\'' +
                ", sex='" + sex + '\'' +
                ", picture='" + picture + '\'' +
                ", fans=" + fans +
                ", songs=" + songs +
                ", albums=" + albums +
                ", category=" + category +
                ", about='" + about + '\'' +
                '}';
    }
}
