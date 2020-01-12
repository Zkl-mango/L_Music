package com.zkl.l_music.vo;

import com.zkl.l_music.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SongListVo implements Serializable {

    private String id;
    private String listName;        /*列表名称*/
    private int category;           /*列表类型，1：自定义；2：我喜欢的*/
    private UserEntity userId;      /*所属用户*/
    List<SongListDetailVo> songListDetailVos; /*歌单下的歌曲信息*/
}
