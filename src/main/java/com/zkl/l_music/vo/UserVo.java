package com.zkl.l_music.vo;

import com.zkl.l_music.dto.FollowsDto;

import java.util.List;

public class UserVo {

    private String id;

    private String name;

    private String phone;

    private String avatar;

    private List<FollowsDto> followsList;

    private List<SongListVo> songListVos;

    private List<SongListVo> likeListVos;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<FollowsDto> getFollowsList() {
        return followsList;
    }

    public void setFollowsList(List<FollowsDto> followsList) {
        this.followsList = followsList;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<SongListVo> getSongListVos() {
        return songListVos;
    }

    public void setSongListVos(List<SongListVo> songListVos) {
        this.songListVos = songListVos;
    }

    public List<SongListVo> getLikeListVos() {
        return likeListVos;
    }

    public void setLikeListVos(List<SongListVo> likeListVos) {
        this.likeListVos = likeListVos;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", followsList=" + followsList +
                ", songListVos=" + songListVos +
                ", likeListVos=" + likeListVos +
                '}';
    }
}
