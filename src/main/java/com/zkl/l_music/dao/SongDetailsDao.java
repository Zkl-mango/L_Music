package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.SongDetailsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SongDetailsDao extends BaseMapper<SongDetailsEntity> {

    boolean deleteSongDetailsByListId(String songList);

    List<SongDetailsEntity> selectSongDetailsByListId(String songList);

    int countSongsByList(String songList);
}
