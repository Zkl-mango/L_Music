package com.zkl.l_music.service;

import com.zkl.l_music.entity.HistoryListEntity;

public interface HistoryListService {

    boolean addHistoryList(HistoryListEntity historyListEntity);

    boolean updateHistoryList(HistoryListEntity historyListEntity);

    boolean deleteHistoryList(String id);

    HistoryListEntity getHistoryListById(String id);
}
