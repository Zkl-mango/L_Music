package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HistoryListVo implements Serializable {

    private String id;
    private SongVo songVo;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;

}
