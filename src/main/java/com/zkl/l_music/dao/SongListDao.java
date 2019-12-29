package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.SongListEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SongListDao extends BaseMapper<SongListEntity> {
}
