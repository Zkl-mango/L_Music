package com.zkl.l_music.service;

import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.vo.SongListVo;

import java.util.List;

public interface LikeListService {


    boolean deleteLikeList(String id,int type);

    /**
     * 获取收藏（点赞）的歌单的信息
     * @param userId
     * @param type
     * @return
     */
    List<SongListVo> getLikeListByUser(String userId, int type);

    /**
     * 获取收藏（点赞）的专辑的信息
     * @param userId
     * @param type
     * @return
     */
    List<AlbumEntity> getLikeListAlbumByUser(String userId, int type);
}
