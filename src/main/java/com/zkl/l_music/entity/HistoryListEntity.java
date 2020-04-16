package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 *  播放历史列表实体
 * @author zkl
 */

@Alias(value = "HistoryListEntity")
@TableName("history_list")
@JsonIgnoreProperties(value = {"handler"})
public class HistoryListEntity implements Serializable {

    @TableId
    private String id;
    private String linkId;
    private UserEntity userId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;              /*播放时间*/
    private int type;               /*类型，1：歌曲；2：专辑；3：歌单*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HistoryListEntity{" +
                "id='" + id + '\'' +
                ", linkId='" + linkId + '\'' +
                ", userId=" + userId +
                ", time=" + time +
                ", type=" + type +
                '}';
    }
}
