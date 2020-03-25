package com.zkl.l_music.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TagVo implements Serializable {

    private String name;
    private List<SmallTagVo> sub;
}
