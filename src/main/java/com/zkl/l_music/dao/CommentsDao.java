package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.entity.CommentsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentsDao extends BaseMapper<CommentsEntity> {

    boolean updateAll(String songId);

    List<CommentsEntity> selectCommentsByLike(String songId);

    IPage selectComments(Page page,String songId);

}
