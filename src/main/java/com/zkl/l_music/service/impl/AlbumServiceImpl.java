package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.AlbumDao;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class AlbumServiceImpl implements AlbumService {

    @Resource
    AlbumDao albumDao;

    @Override
    public boolean addAlbum(AlbumEntity albumEntity) {
        int res = albumDao.insert(albumEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAlbum(AlbumEntity albumEntity) {
        int res = albumDao.updateById(albumEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAlbum(String id) {
        int res = albumDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public AlbumEntity getAlbumById(String id) {
        return albumDao.selectById(id);
    }
}
