package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.SongListDao;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class SongListServiceImpl implements SongListService {

    @Resource
    SongListDao songListDao;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    SongDetailsService songDetailsService;

    @Override
    public String addSongList(SongListEntity songListEntity, UserEntity userEntity) {
        songListEntity.setId(uuidGenerator.generateUUID());
        songListEntity.setUserId(userEntity);
        int res = songListDao.insert(songListEntity);
        if(res == 1) {
            return songListEntity.getId();
        }
        return null;
    }

    @Override
    public boolean updateSongList(String id,String name) {
        SongListEntity songListEntity = songListDao.selectById(id);
        if(songListEntity==null) {
            return false;
        }
        songListEntity.setListName(name);
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
    public SongListVo getSongListById(String id) {
        SongListEntity songListEntity = songListDao.selectById(id);
        if(songListEntity == null) {
            return null;
        }
        SongListVo songListVo = new SongListVo();
        BeanUtils.copyProperties(songListVo,songListEntity);
        List<SongListDetailVo> list = songDetailsService.getSongDetailsByList(id);
        songListVo.setSongListDetailVos(list);
        return songListVo;
    }

    @Override
    public List<SongListEntity> getSongListByUser(String userId) {
        return songListDao.selectSongListByUser(userId);
    }
}
