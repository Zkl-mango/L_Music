package com.zkl.l_music.service;

import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.HistoryListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.AlbumVo;
import com.zkl.l_music.vo.HistoryListVo;
import com.zkl.l_music.vo.SongListVo;

import java.util.List;

public interface HistoryListService {

    boolean addHistoryList(String songId,  String userId,int type);

    boolean updateHistoryList(HistoryListEntity historyListEntity);

    boolean deleteHistoryList(String id);

    boolean deleteHistoryLists(String userId,int type);

    HistoryListEntity getHistoryListById(String id);

    /**
     * 查找历史记录的歌曲
     * @param userId
     * @return
     */
    List<HistoryListVo> getHistoryListByUser(String userId);

    /**
     * 查找历史记录的歌单
     * @param userId
     * @param type
     * @return
     */
    List<SongListVo> getHistorySongByUser(String userId,int type);
    /**
     * 查找历史记录的专辑
     * @param userId
     * @param type
     * @return
     */
    List<AlbumVo> getHistoryAlbumByUser(String userId, int type);
}
