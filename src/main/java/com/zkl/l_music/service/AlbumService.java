package com.zkl.l_music.service;

import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.vo.AlbumDetailVo;

import java.util.List;

public interface AlbumService {

    boolean addAlbum(AlbumEntity albumEntity);

    boolean updateAlbum(String id,int flag);

    boolean deleteAlbum(String id);

    AlbumDetailVo getAlbumById(String id);

    List<AlbumEntity> getAlbumsBySinger(String singerId);

    List<AlbumEntity> getNewAlbums();
}
