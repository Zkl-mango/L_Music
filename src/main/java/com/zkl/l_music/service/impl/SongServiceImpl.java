package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SongServiceImpl implements SongService {

    @Resource
    SongDao songDao;

    @Override
    public boolean addSong(SongEntity songEntity) {
        int res = songDao.insert(songEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSong(SongEntity songEntity) {
        int res = songDao.updateById(songEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSong(String id) {
        int res = songDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public SongEntity getSongById(String id) {
        return songDao.selectById(id);
    }
}
