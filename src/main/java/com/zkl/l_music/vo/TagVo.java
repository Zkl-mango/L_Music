package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class TagVo implements Serializable {

    private String name;
    private List<SmallTagVo> sub;
}
