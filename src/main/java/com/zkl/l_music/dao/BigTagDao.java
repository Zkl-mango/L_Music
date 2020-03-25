package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.BigTagEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BigTagDao  extends BaseMapper<BigTagEntity> {

    List<BigTagEntity> selectAll();
}
