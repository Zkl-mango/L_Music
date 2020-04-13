package com.zkl.l_music.service;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.vo.AlbumDetailVo;

import java.awt.print.Pageable;
import java.util.List;

public interface AlbumService {

    boolean addAlbum(AlbumEntity albumEntity);

    boolean updateAlbumByFlag(String id,int flag,String userId);

    boolean updateAlbum(AlbumEntity albumEntity);

    boolean deleteAlbum(String id);

    AlbumDetailVo getAlbumById(String id,String userId);

    AlbumEntity getAlbumEntityById(String id);

    List<AlbumEntity> getAlbumsBySinger(PageBo pageBo,String singerId);

    List<AlbumEntity> getAllAlbumsBySinger(String singerId);

    /**
     * 查找最新的3个专辑，除了本专辑以外
     * @return
     */
    List<AlbumEntity> getNewAlbumsBySinger(String id,String singerId);
    /**
     * 查找全部最新的3个专辑
     * @return
     */
    List<AlbumEntity> getNewAlbum();
}
