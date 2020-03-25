package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.AlbumDao;
import com.zkl.l_music.dao.HistoryListDao;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.dao.SongListDao;
import com.zkl.l_music.entity.*;
import com.zkl.l_music.service.HistoryListService;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.UUIDGenerator;
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
    SongDao songDao;
    @Resource
    AlbumDao albumDao;
    @Resource
    SongListDao songListDao;
    @Resource
    SongDetailsService songDetailsService;
    @Resource
    SongService songService;

    @Override
    public boolean addHistoryList(String songId, UserEntity userEntity) {
        HistoryListEntity historyListEntity = new HistoryListEntity();
        historyListEntity.setId(uuidGenerator.generateUUID());
        historyListEntity.setTime(new Date());
        historyListEntity.setUserId(userEntity);
        SongEntity songEntity = songDao.selectById(songId);
        historyListEntity.setLinkId(songEntity.getId());
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
    public List<SongListVo> getHistoryAlbumSongByUser(String userId, int type) {
        List<HistoryListEntity> list = historyListDao.selectHistorysByUser(userId,type);
        List<SongListVo> songListVos = new ArrayList<>();
        //专辑历史
        if(type == ConstantUtil.albumType) {
            for(int i=0;i<list.size();i++) {
                AlbumEntity albumEntity = albumDao.selectById(list.get(i).getLinkId());
                SongListVo songListVo = new SongListVo();
                BeanUtils.copyProperties(albumEntity,songListVo);
                songListVo.setListName(albumEntity.getName());
                songListVo.setSongNum(albumEntity.getSongs());
                songListVos.add(songListVo);
            }
        }
        //歌单专辑历史
        else if(type == ConstantUtil.songType) {
            for(int i=0;i<list.size();i++) {
                SongListEntity songListEntity = songListDao.selectById(list.get(i).getLinkId());
                SongListVo songListVo = new SongListVo();
                BeanUtils.copyProperties(songListEntity,songListVo);
                songListVo.setSongNum(songDetailsService.countSongDetailsByList(list.get(i).getId()));
                songListVos.add(songListVo);
            }
        }
        return songListVos;
    }
}
