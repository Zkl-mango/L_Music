package com.zkl.l_music.service;

import com.zkl.l_music.entity.SongDetailsEntity;

public interface SongDetailsService {

    boolean addSongDetails(SongDetailsEntity songDetailsEntity);

    boolean updateSongDetails(SongDetailsEntity songDetailsEntity);

    boolean deleteSongDetails(String id);

    SongDetailsEntity getSongDetailsById(String id);
}
