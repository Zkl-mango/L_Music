package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;      /*发表时间*/
    private int hot;        /*热度*/
    private List<SongVo> songVoList; /*专辑下的歌曲*/
}
