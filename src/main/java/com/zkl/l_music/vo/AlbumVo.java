package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zkl.l_music.entity.SingerEntity;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class AlbumVo {

    private String id;

    private SingerEntity singerId;
    private String listName;
    private String picture;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;      /*发表时间*/
    private int hot;        /*热度=点赞数*/
    private String type;    /*类型*/
    private int songNum;      /*歌曲数*/
    private String singerList;      /*多个歌手的id集合*/
}
