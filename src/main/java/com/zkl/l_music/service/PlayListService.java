package com.zkl.l_music.service;

import com.zkl.l_music.entity.PlayListEntity;
import com.zkl.l_music.vo.PlayListVo;
import com.zkl.l_music.vo.SongListDetailVo;

import java.util.List;

public interface PlayListService {

    String addPlayList(PlayListVo playListVo);

    boolean updatePlayList(String id,String userId);

    boolean deletePlayList(String id);

    boolean deletePlayListByUser(String userId);

    PlayListEntity getPlayListById(String id);

    List<SongListDetailVo> getPlayListByUser(String userId);
}
