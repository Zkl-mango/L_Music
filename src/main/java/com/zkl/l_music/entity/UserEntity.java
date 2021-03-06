package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zkl.l_music.util.ConstantUtil;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 用户实体
 * @author zkl
 */
@Alias(value = "UserEntity")
@TableName("user")
@JsonIgnoreProperties(value = {"handler"})
public class UserEntity implements Serializable {

    @TableId
    private String id;
    private String name;
    private String phone;
    private String password;
    private String avatar;
    private String follow;      /*关注的歌手id*/

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", follow='" + follow + '\'' +
                '}';
    }
}
