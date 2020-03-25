package com.zkl.l_music.service;

import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.SongListVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongListService {

    String addSongList(String songListName, String id,int category);

    boolean updateSongList(SongListEntity songListEntity);

    boolean updateSongListPicture(String id, MultipartFile file);

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
