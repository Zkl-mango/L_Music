package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zkl.l_music.entity.SingerEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class SingerListVo implements Serializable {

    private String letter;
    private String text;
    private List<SingerVo> singerVoList;


}
