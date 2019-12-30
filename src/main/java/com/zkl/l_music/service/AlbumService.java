package com.zkl.l_music.service;

import com.zkl.l_music.entity.AlbumEntity;

public interface AlbumService {

    boolean addAlbum(AlbumEntity albumEntity);

    boolean updateAlbum(AlbumEntity albumEntity);

    boolean deleteAlbum(String id);

    AlbumEntity getAlbumById(String id);
}
