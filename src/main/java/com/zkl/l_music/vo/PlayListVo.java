package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class PlayListVo implements Serializable {

    private String id;
    private String userId;
    private String songId;
    private int isPlay;
}
