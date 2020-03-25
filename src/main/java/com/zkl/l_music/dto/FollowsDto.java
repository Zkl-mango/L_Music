package com.zkl.l_music.dto;

import java.io.Serializable;

public class FollowsDto implements Serializable {

    private String id;
    private String name;
    private String picture;
    private int fans;

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

    @Override
    public String toString() {
        return "FollowsDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", fans=" + fans +
                '}';
    }
}
