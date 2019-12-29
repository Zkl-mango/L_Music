package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 专辑信息实体
 * @author zkl
 */
@Alias(value = "AlbumEntity")
@TableName("album")
public class AlbumEntity implements Serializable {

    @TableId
    private String id;

    private SingerEntity singerId;
    private String name;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;      /*发表时间*/
    private int hot;        /*热度*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SingerEntity getSingerId() {
        return singerId;
    }

    public void setSingerId(SingerEntity singerId) {
        this.singerId = singerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    @Override
    public String toString() {
        return "AlbumEntity{" +
                "id='" + id + '\'' +
                ", singerId=" + singerId +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", hot=" + hot +
                '}';
    }
}
