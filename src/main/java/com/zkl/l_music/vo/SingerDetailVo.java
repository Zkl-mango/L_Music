package com.zkl.l_music.vo;

import com.zkl.l_music.entity.AlbumEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SingerDetailVo implements Serializable {

    private String id;
    private String singer;
    private String englishName;
    private String about;           /*关于ta*/
    private String sex;
    private String picture;         /*个人封面图片*/
    private int fans;               /*粉丝数*/
    private int songs;              /*歌曲数*/
    private int albums;             /*专辑数*/
    private List<AlbumEntity> albumList; /*专辑列表（最新的10张）分页*/
    private List<SongVo> songList;  /*歌曲列表（最热的10首）分页*/
}
