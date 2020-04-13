package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.*;
import com.zkl.l_music.entity.*;
import com.zkl.l_music.service.HistoryListService;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.AlbumVo;
import com.zkl.l_music.vo.HistoryListVo;
import com.zkl.l_music.vo.SongListVo;
import com.zkl.l_music.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class HistoryListServiceImpl implements HistoryListService {

    @Resource
    HistoryListDao historyListDao;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    UserDao userDao;
    @Resource
    AlbumDao albumDao;
    @Resource
    SongListDao songListDao;
    @Resource
    SongDetailsService songDetailsService;
    @Resource
    SongService songService;

    @Override
    public boolean addHistoryList(String songId, String userId,int type) {
        HistoryListEntity historyListEntity = historyListDao.selectHistoryByUserAndSong(userId,songId,type);
        if(historyListEntity != null) {
            historyListEntity.setTime(new Date());
            historyListEntity.setUserId(null);
            historyListDao.updateById(historyListEntity);
            return true;
        }
        historyListEntity = new HistoryListEntity();
        historyListEntity.setId(uuidGenerator.generateUUID());
        historyListEntity.setTime(new Date());
        historyListEntity.setUserId(userDao.selectById(userId));
        historyListEntity.setLinkId(songId);
        historyListEntity.setType(type);
        int res = historyListDao.insert(historyListEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateHistoryList(HistoryListEntity historyListEntity) {
        int res = historyListDao.updateById(historyListEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteHistoryList(String id) {
        int res = historyListDao.deleteById(id);
        if( res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteHistoryLists(String userId, int type) {
        int res = historyListDao.deletedHistorys(userId,type);
        return true;
    }

    @Override
    public HistoryListEntity getHistoryListById(String id) {
        return historyListDao.selectById(id);
    }

    /**
     * 获取用户历史播放歌曲列表
     * @param userId
     * @return
     */
    @Override
    public List<HistoryListVo> getHistoryListByUser(String userId) {
        List<HistoryListEntity> list = historyListDao.selectHistorysByUser(userId,1);
        List<HistoryListVo> historyListVos = new ArrayList<>();
        for (int i=0;i<list.size();i++) {
            HistoryListEntity historyListEntity = list.get(i);
            HistoryListVo historyListVo = new HistoryListVo();
            BeanUtils.copyProperties(historyListEntity,historyListVo);
            SongVo songVo = songService.getSongById(historyListEntity.getLinkId());
            historyListVo.setSongVo(songVo);
            historyListVos.add(historyListVo);
        }
        return historyListVos;
    }

    @Override
    public List<SongListVo> getHistorySongByUser(String userId, int type) {
        List<HistoryListEntity> list = historyListDao.selectHistorysByUser(userId,type);
        List<SongListVo> songListVos = new ArrayList<>();
        //歌单专辑历史
        if(type == ConstantUtil.listType) {
            for(int i=0;i<list.size();i++) {
                SongListEntity songListEntity = songListDao.selectById(list.get(i).getLinkId());
                SongListVo songListVo = new SongListVo();
                BeanUtils.copyProperties(songListEntity,songListVo);
                songListVo.setSongNum(songDetailsService.countSongDetailsByList(list.get(i).getLinkId()));
                songListVos.add(songListVo);
            }
        }
        return songListVos;
    }

    @Override
    public List<AlbumVo> getHistoryAlbumByUser(String userId, int type) {
        List<HistoryListEntity> list = historyListDao.selectHistorysByUser(userId,type);
        List<AlbumVo> albumEntityList = new ArrayList<>();
        //专辑历史
        if(type == ConstantUtil.albumType) {
            for(int i=0;i<list.size();i++) {
                AlbumEntity albumEntity = albumDao.selectById(list.get(i).getLinkId());
                AlbumVo albumVo = new AlbumVo();
                BeanUtils.copyProperties(albumEntity,albumVo);
                albumVo.setSongNum(albumEntity.getSongs());
                albumVo.setListName(albumEntity.getName());
                albumEntityList.add(albumVo);
            }
        }
        return albumEntityList;
    }
}
