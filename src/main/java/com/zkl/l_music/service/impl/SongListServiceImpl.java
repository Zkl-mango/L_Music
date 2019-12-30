package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.SongListDao;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.service.SongListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SongListServiceImpl implements SongListService {

    @Resource
    SongListDao songListDao;

    @Override
    public boolean addSongList(SongListEntity songListEntity) {
        int res = songListDao.insert(songListEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSongList(SongListEntity songListEntity) {
        int res = songListDao.updateById(songListEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSongList(String id) {
        int res = songListDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public SongListEntity getSongListById(String id) {
        return songListDao.selectById(id);
    }
}
