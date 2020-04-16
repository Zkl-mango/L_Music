package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias(value = "SmallTagEntity")
@TableName("small_tag")
@JsonIgnoreProperties(value = {"handler"})
public class SmallTagEntity implements Serializable {

    @TableId
    private String id;

    private String name;
    private int category;   /*所属类别*/
    private int hot;        /*是否热，1：热，2 不热*/

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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    @Override
    public String toString() {
        return "SmallTagEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", hot=" + hot +
                '}';
    }
}
