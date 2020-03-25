package com.zkl.l_music.vo;

import com.zkl.l_music.entity.SingerEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SingerListVo implements Serializable {

    private String letter;
    private String text;
    private List<SingerVo> singerVoList;


}
