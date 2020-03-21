package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 榜单排行实体
 * @author zhengkunli
 */
@Alias(value = "RankEntity")
@TableName("rank")
public class RankEntity implements Serializable {
    @TableId
    private String id;
    private String rankName;    /*榜单名称*/
    private int playNum;        /*榜单播放量*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getPlayNum() {
        return playNum;
    }

    public void setPlayNum(int playNum) {
        this.playNum = playNum;
    }

    @Override
    public String toString() {
        return "RankEntity{" +
                "id='" + id + '\'' +
                ", rankName='" + rankName + '\'' +
                ", playNum=" + playNum +
                '}';
    }
}
