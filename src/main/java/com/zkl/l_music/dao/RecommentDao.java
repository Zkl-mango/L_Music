package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.RecommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecommentDao extends BaseMapper<RecommentEntity> {

    int deleteRecomments();

    List<RecommentEntity> selectRecommentsByUser(String userId,int linkType);
}
