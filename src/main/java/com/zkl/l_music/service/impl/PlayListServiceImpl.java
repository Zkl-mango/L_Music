package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.PlayListDao;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.PlayListEntity;
import com.zkl.l_music.service.PlayListService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.PlayListVo;
import com.zkl.l_music.vo.SongListDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PlayListServiceImpl implements PlayListService {

    @Resource
    PlayListDao playListDao;
    @Resource
    SongService songService;
    @Resource
    UUIDGenerator uuidGenerator;

    @Override
    public String addPlayList(PlayListVo playListVo) {
        playListVo.setId(uuidGenerator.generateUUID());
        int res = playListDao.insertPlayList(playListVo);
        if(res == 1) {
            return playListVo.getId();
        }
        return "";
    }

    @Override
    public boolean updatePlayList(String id,String userId) {
        List<PlayListEntity> playListEntities = playListDao.selectPlaysByUser(userId);
        for(int i=0;i<playListEntities.size();i++) {
            playListEntities.get(i).setSongId(null);
            playListEntities.get(i).setUserId(null);
            playListEntities.get(i).setIsPlay(0);
            playListDao.updateById(playListEntities.get(i));
        }
        PlayListEntity playListEntity = playListDao.selectById(id);
        if(playListEntity == null) {
            return false;
        }
        playListEntity.setSongId(null);
        playListEntity.setUserId(null);
        playListEntity.setIsPlay(1);
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
    public boolean deletePlayListByUser(String userId) {
        int res = playListDao.deletedByUser(userId);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public PlayListEntity getPlayListById(String id) {
        return playListDao.selectById(id);
    }

    @Override
    public List<SongListDetailVo> getPlayListByUser(String userId) {
        List<SongListDetailVo> list = new ArrayList<>();
        List<PlayListEntity> playListEntities = playListDao.selectPlaysByUser(userId);
        for(int i=0;i<playListEntities.size();i++) {
            SongListDetailVo songListDetailVo = new SongListDetailVo();
            songListDetailVo.setId(playListEntities.get(i).getId());
            songListDetailVo.setSongVo(songService.songChangeVo(playListEntities.get(i).getSongId()));
            songListDetailVo.getSongVo().setLink(String.valueOf(playListEntities.get(i).getIsPlay()));
        }
        return list;
    }
}
