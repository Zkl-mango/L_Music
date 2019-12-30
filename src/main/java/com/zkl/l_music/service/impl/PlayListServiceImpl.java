package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.PlayListDao;
import com.zkl.l_music.entity.PlayListEntity;
import com.zkl.l_music.service.PlayListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class PlayListServiceImpl implements PlayListService {

    @Resource
    PlayListDao playListDao;

    @Override
    public boolean addPlayList(PlayListEntity playListEntity) {
        int res = playListDao.insert(playListEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePlayList(PlayListEntity playListEntity) {
        int res = playListDao.updateById(playListEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePlayList(String id) {
        int res = playListDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public PlayListEntity getPlayListById(String id) {
        return playListDao.selectById(id);
    }
}
