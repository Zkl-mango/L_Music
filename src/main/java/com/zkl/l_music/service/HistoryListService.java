package com.zkl.l_music.service;

import com.zkl.l_music.entity.HistoryListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.HistoryListVo;
import com.zkl.l_music.vo.SongListVo;

import java.util.List;

public interface HistoryListService {

    boolean addHistoryList(String songId, UserEntity userEntity);

    boolean updateHistoryList(HistoryListEntity historyListEntity);

    boolean deleteHistoryList(String id);

    HistoryListEntity getHistoryListById(String id);

    /**
     * 查找历史记录的歌曲
     * @param userId
     * @return
     */
    List<HistoryListVo> getHistoryListByUser(String userId);

    /**
     * 查找历史记录的歌单和专辑
     * @param userId
     * @param type
     * @return
     */
    List<SongListVo> getHistoryAlbumSongByUser(String userId,int type);

}
