package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.CommentsLikeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentsLikeDao extends BaseMapper<CommentsLikeEntity> {

    CommentsLikeEntity selectCommentsLike(String userId,String commentId);

    List<CommentsLikeEntity> selectCommentsIsLike(String userId);
}
