package com.zkl.l_music.service;

import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.SongListVo;

import java.util.List;

public interface SongListService {

    String addSongList(SongListEntity songListEntity, UserEntity userEntity);

    boolean updateSongList(String id,String name);

    boolean deleteSongList(String id);

    SongListVo getSongListById(String id);

    /**
     * 获取自定义和喜欢歌单的信息
     * @param userId
     * @param category
     * @return
     */
    List<SongListVo> getSongListByUser(String userId,int category);
}
