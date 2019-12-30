package com.zkl.l_music.service;

import com.zkl.l_music.entity.PlayListEntity;

public interface PlayListService {

    boolean addPlayList(PlayListEntity playListEntity);

    boolean updatePlayList(PlayListEntity playListEntity);

    boolean deletePlayList(String id);

    PlayListEntity getPlayListById(String id);
}
