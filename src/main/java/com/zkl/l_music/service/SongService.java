package com.zkl.l_music.service;

import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SongVo;

import java.util.List;

public interface SongService {

    boolean addSong(SongEntity songEntity);

    boolean updateSong(String id,int hot,int recommend,int increase);

    boolean deleteSong(String id);

    SongVo getSongById(String id);

    List<SongVo> getSongsByAlbum(String albumId);

    PageInfoVo getSongsByCategory(int category);

    PageInfoVo getSongsBySinger(String SingerId);
}
