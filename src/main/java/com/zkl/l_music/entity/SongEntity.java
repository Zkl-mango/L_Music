package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Objects;

/**
 * 歌曲信息实体
 * @author zkl
 */
@Alias(value = "SongEntity")
@TableName("song")
@JsonIgnoreProperties(value = {"handler"})
public class SongEntity implements Serializable {

    @TableId
    private String id;

    private String name;            /*歌曲名称*/
    private String singerId;        /*歌手id*/
    private String albumId;         /*所属专辑id*/
    private String link;            /*歌曲连接*/
    private String picture;         /*歌曲封面*/
    private String lyric;           /*歌词*/
    private String klyric;          /*歌词*/
    private int likeNum;               /*点赞数*/
    private String category;           /*歌曲类别*/
    private int hot;                /*歌曲热度*/
    private int recommend;          /*歌曲评论数*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public String getKlyric() {
        return klyric;
    }

    public void setKlyric(String klyric) {
        this.klyric = klyric;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    @Override
    public String toString() {
        return "SongEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", singerId='" + singerId + '\'' +
                ", albumId='" + albumId + '\'' +
                ", link='" + link + '\'' +
                ", picture='" + picture + '\'' +
                ", lyric='" + lyric + '\'' +
                ", klyric='" + klyric + '\'' +
                ", likeNum=" + likeNum +
                ", category=" + category +
                ", hot=" + hot +
                ", recommend=" + recommend +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongEntity that = (SongEntity) o;
        return likeNum == that.likeNum &&
                hot == that.hot &&
                recommend == that.recommend &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(singerId, that.singerId) &&
                Objects.equals(albumId, that.albumId) &&
                Objects.equals(link, that.link) &&
                Objects.equals(picture, that.picture) &&
                Objects.equals(lyric, that.lyric) &&
                Objects.equals(klyric, that.klyric) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return id.hashCode() * name.hashCode();
    }

}
