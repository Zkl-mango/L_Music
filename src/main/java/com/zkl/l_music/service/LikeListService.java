package com.zkl.l_music.service;

import com.zkl.l_music.vo.SongListVo;

import java.util.List;

public interface LikeListService {


    /**
     * 获取收藏（点赞）的歌单、专辑的信息
     * @param userId
     * @param type
     * @return
     */
    List<SongListVo> getSongListByUser(String userId, int type);
}
