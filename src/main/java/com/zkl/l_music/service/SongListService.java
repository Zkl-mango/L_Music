package com.zkl.l_music.service;

import com.zkl.l_music.entity.SongListEntity;

public interface SongListService {

    boolean addSongList(SongListEntity songListEntity);

    boolean updateSongList(SongListEntity songListEntity);

    boolean deleteSongList(String id);

    SongListEntity getSongListById(String id);
}
