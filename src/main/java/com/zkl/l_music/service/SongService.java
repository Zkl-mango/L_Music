package com.zkl.l_music.service;

import com.zkl.l_music.entity.SongEntity;

public interface SongService {

    boolean addSong(SongEntity songEntity);

    boolean updateSong(SongEntity songEntity);

    boolean deleteSong(String id);

    SongEntity getSongById(String id);
}
