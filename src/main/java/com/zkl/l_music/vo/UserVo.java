package com.zkl.l_music.vo;

import com.zkl.l_music.dto.FollowsDto;

import java.util.List;

public class UserVo {

    private String id;

    private String name;

    private String phone;

    private List<FollowsDto> followsList;

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

    @Override
    public String toString() {
        return "UserVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", followsList=" + followsList +
                '}';
    }
}
