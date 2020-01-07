package com.zkl.l_music.service;

import com.zkl.l_music.entity.AlbumEntity;

import java.util.List;

public interface AlbumService {

    boolean addAlbum(AlbumEntity albumEntity);

    boolean updateAlbum(String id,int flag);

    boolean deleteAlbum(String id);

    AlbumEntity getAlbumById(String id);

    List<AlbumEntity> getAlbumsBySinger(String singerId);

    List<AlbumEntity> getNewAlbums();
}
