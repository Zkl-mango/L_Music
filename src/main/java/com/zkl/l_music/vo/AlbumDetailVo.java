package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.SingerEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AlbumDetailVo implements Serializable {

    private String id;

    private SingerEntity singerId;
    private String name;
    private String picture;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;      /*发表时间*/
    private int hot;        /*热度=点赞数*/
    private String type;    /*类型*/
    private int songs;      /*歌曲数*/
    private List<SongVo> songVoList; /*专辑下的歌曲*/
    private List<AlbumEntity> moreAlbums;       /*歌手其他三个专辑*/
    private int like;       /*是否被用户收藏*/
}
