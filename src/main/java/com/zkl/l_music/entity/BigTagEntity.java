package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Alias(value = "BigTagEntity")
@TableName("big_tag")
public class BigTagEntity implements Serializable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BigTagEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
