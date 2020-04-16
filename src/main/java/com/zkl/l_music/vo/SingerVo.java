package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class SingerVo implements Serializable {

    private String id;

    private String singer;
    private String sex;
    private String picture;         /*个人封面图片*/
    private int fans;               /*粉丝数*/
    private int category;           /*类别*/
    private String follow;          /*是否被关注，默认没有被关注*/
}
