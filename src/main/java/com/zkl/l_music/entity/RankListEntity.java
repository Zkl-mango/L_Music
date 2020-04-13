package com.zkl.l_music.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 榜单排行详情实体
 * @author zhengkunli
 */
@Alias(value = "RankListEntity")
@TableName("rank_list")
public class RankListEntity implements Serializable {

    @TableId
    private String id;
    private RankEntity rankId;
    private SongEntity songId;
    private int recomment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RankEntity getRankId() {
        return rankId;
    }

    public void setRankId(RankEntity rankId) {
        this.rankId = rankId;
    }

    public SongEntity getSongId() {
        return songId;
    }

    public void setSongId(SongEntity songId) {
        this.songId = songId;
    }

    public int getRecomment() {
        return recomment;
    }

    public void setRecomment(int recomment) {
        this.recomment = recomment;
    }

    @Override
    public String toString() {
        return "RankListEntity{" +
                "id='" + id + '\'' +
                ", rankId=" + rankId +
                ", songId=" + songId +
                ", recomment=" + recomment +
                '}';
    }
}
