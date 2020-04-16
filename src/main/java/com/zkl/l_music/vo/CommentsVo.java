package com.zkl.l_music.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zkl.l_music.entity.CommentsEntity;
import lombok.Data;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class CommentsVo implements Serializable {

    List<CommentsDetailVo> commentsEntityList;
    PageInfoVo commentsPage;

}
