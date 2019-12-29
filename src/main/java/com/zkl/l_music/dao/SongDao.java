package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.SongEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SongDao extends BaseMapper<SongEntity> {
}
