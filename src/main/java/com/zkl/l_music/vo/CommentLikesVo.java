package com.zkl.l_music.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class CommentLikesVo implements Serializable {

    private String id;
    private String content;
    private String songname;
    private String username;    /*评论人的名字*/
    private int num;
    private boolean iscancle;
}
