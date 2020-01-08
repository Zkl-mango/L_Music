package com.zkl.l_music.vo;

import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.SingerEntity;
import lombok.Data;

@Data
public class SongVo {

    private String id;

    private String name;            /*歌曲名称*/
    private SingerEntity singer;        /*歌手id*/
    private AlbumEntity album;         /*所属专辑id*/
    private String link;            /*歌曲连接*/
    private String picture;         /*歌曲封面*/
    private String lyric;           /*歌词*/
    private String duration;        /*歌曲时长*/
    private int category;           /*歌曲类别*/
    private int hot;                /*歌曲热度*/
    private int recommend;          /*歌曲评论数*/
    private int increase;           /*歌曲被添加次数*/
}
