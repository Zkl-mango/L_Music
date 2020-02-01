package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentsDetailVo implements Serializable {

    private String id;

    private String comment;
    private UserEntity userId;
    private SongEntity songId;
    private int likes;          /*点赞数*/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date time;          /*评论时间*/
    private int isHot;          /*是否为热度展示,0否，1是*/
    private int isUser;         /*是否点赞，0否，1是*/
}
