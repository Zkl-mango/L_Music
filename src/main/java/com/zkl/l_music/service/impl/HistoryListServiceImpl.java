package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.HistoryListDao;
import com.zkl.l_music.entity.HistoryListEntity;
import com.zkl.l_music.service.HistoryListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class HistoryListServiceImpl implements HistoryListService {

    @Resource
    HistoryListDao historyListDao;

    @Override
    public boolean addHistoryList(HistoryListEntity historyListEntity) {
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
}
