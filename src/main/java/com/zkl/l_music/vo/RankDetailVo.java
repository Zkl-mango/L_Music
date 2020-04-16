package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.RankListEntity;
import com.zkl.l_music.entity.SongEntity;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class RankDetailVo {

    private String id;
    private SongVo songVo;
    private int recomment;
}
