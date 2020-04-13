package com.zkl.l_music.vo;

import lombok.Data;

@Data
public class PlayListVo {

    private String id;
    private String userId;
    private String songId;
    private int isPlay;
}
