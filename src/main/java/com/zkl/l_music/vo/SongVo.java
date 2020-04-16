package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.SingerEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class SongVo implements Serializable {

    private String id;

    private String name;            /*歌曲名称*/
    private List<SingerEntity> singerList;        /*歌手id*/
    private AlbumEntity album;         /*所属专辑id*/
    private String link;            /*歌曲连接*/
    private String picture;         /*歌曲封面*/
    private String lyric;           /*歌词*/
    private String klyric;           /*歌词*/
    private int like;               /*点赞数*/
    private int category;           /*歌曲类别*/
    private int hot;                /*歌曲热度*/
    private int recommend;          /*歌曲评论数*/
}
