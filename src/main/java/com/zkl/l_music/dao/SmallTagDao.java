package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.SmallTagEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SmallTagDao extends BaseMapper<SmallTagEntity> {

    List<SmallTagEntity> selectTagsByCategory(int category);

    List<SmallTagEntity> selectTagsByHot(int hot);
}
