package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 歌曲列表实体
 * @author zkl
 */
@Alias(value = "SongListEntity")
@TableName("song_list")
public class SongListEntity implements Serializable {

    @TableId
    private String id;

    private String listName;        /*列表名称*/
    private int category;           /*列表类型，1：自定义；2：我喜欢的*/
    private UserEntity userId;      /*所属用户*/

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

    @Override
    public String toString() {
        return "SongListEntity{" +
                "id='" + id + '\'' +
                ", listName='" + listName + '\'' +
                ", category=" + category +
                ", userId=" + userId +
                '}';
    }
}
