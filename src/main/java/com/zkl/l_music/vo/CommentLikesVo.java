package com.zkl.l_music.vo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class CommentLikesVo implements Serializable {

    private String id;
    private String content;
    private String songname;
    private String username;    /*评论人的名字*/
    private int num;
    private boolean iscancle;
}
