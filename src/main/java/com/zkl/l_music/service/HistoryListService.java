package com.zkl.l_music.service;

import com.zkl.l_music.entity.HistoryListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.HistoryListVo;

import java.util.List;

public interface HistoryListService {

    boolean addHistoryList(String songId, UserEntity userEntity);

    boolean updateHistoryList(HistoryListEntity historyListEntity);

    boolean deleteHistoryList(String id);

    HistoryListEntity getHistoryListById(String id);

    List<HistoryListVo> getHistoryListByUser(String userId);
}
