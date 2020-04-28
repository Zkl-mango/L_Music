package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.entity.SongListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SongListDao extends BaseMapper<SongListEntity> {

    List<SongListEntity> selectSongListByUser(String userId,int category);

    List<SongListEntity> selectPlaySongList();

    List<SongListEntity> selectLikeSongList();

    IPage<SongListEntity> selectSongListByTag(Page page,String tag);

    IPage<SongListEntity> selectAllSongList(Page page);
}
