package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.SongDetailsDao;
import com.zkl.l_music.entity.SongDetailsEntity;
import com.zkl.l_music.service.SongDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SongDetailsServiceImpl implements SongDetailsService {

    @Resource
    SongDetailsDao songDetailsDao;

    @Override
    public boolean addSongDetails(SongDetailsEntity songDetailsEntity) {
        int res = songDetailsDao.insert(songDetailsEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSongDetails(SongDetailsEntity songDetailsEntity) {
        int res = songDetailsDao.updateById(songDetailsEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSongDetails(String id) {
        int res = songDetailsDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public SongDetailsEntity getSongDetailsById(String id) {
        return songDetailsDao.selectById(id);
    }
}
