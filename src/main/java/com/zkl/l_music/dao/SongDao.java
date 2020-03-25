package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.entity.SongEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SongDao extends BaseMapper<SongEntity> {

    List<SongEntity> selectSongsByAlbum(String albumId);

    IPage<SongEntity> selectSongsByCategory(Page page,int category);

    IPage<SongEntity> selectSongsBySinger(Page page,String singerId);

}
