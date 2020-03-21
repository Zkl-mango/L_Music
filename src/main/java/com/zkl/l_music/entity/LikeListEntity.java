package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 我喜欢的模块实体
 * @author zhengkunli
 */
@Alias(value = "LikeListEntity")
@TableName("like_list")
public class LikeListEntity implements Serializable {

    @TableId
    private String id;
    private UserEntity userId;  /*用户*/
    private String linkId;          /*专辑、歌单连接id*/
    private int type;               /*类型，2：专辑；3：歌单*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;              /*添加时间*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LikeListEntity{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", linkId='" + linkId + '\'' +
                ", type=" + type +
                ", time=" + time +
                '}';
    }
}
