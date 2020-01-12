package com.zkl.l_music.vo;

import com.zkl.l_music.entity.CommentsEntity;
import lombok.Data;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Data
public class CommentsVo implements Serializable {

    List<CommentsEntity> commentsEntityList;
    PageInfoVo commentsPage;

}
