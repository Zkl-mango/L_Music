package com.zkl.l_music.service;

import com.zkl.l_music.bo.SongDetailBo;
import com.zkl.l_music.entity.SongDetailsEntity;
import com.zkl.l_music.vo.SongListDetailVo;

import java.util.List;

public interface SongDetailsService {

    boolean addSongDetails(SongDetailBo songDetailBo);

    boolean updateSongDetails(String id);

    boolean deleteSongDetails(String id);

    boolean deleteSongDetailsByList(String listId);

    SongDetailsEntity getSongDetailsById(String id);

    List<SongListDetailVo> getSongDetailsByList(String listId);
}
