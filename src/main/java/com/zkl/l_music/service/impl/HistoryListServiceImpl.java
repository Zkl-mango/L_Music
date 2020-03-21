package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.HistoryListDao;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.entity.HistoryListEntity;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.HistoryListService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.HistoryListVo;
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
     * 获取用户历史播放列表
     * @param userId
     * @return
     */
    @Override
    public List<HistoryListVo> getHistoryListByUser(String userId) {
        List<HistoryListEntity> list = historyListDao.selectHistorysByUser(userId);
        List<HistoryListVo> historyListVos = new ArrayList<>();
        for (int i=0;i<list.size();i++) {
            HistoryListEntity historyListEntity = list.get(i);
            HistoryListVo historyListVo = new HistoryListVo();
            BeanUtils.copyProperties(historyListVo,historyListEntity);
            SongVo songVo = songService.getSongById(historyListEntity.getLinkId());
            historyListVo.setSongVo(songVo);
            historyListVos.add(historyListVo);
        }
        return historyListVos;
    }
}
