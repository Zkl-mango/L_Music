package com.zkl.l_music.service;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SongVo;

import java.util.List;

public interface SongService {

    boolean addSong(SongEntity songEntity);

    boolean updateSong(String id,int hot,int recommend,int increase);

    boolean deleteSong(String id);

    SongVo getSongById(String id);

    /**
     * 根据专辑获取专辑下的所有歌曲
     * @param albumId
     * @return
     */
    List<SongVo> getSongsByAlbum(String albumId);

    /**
     * 获取某个类别下的所有歌曲（分页）
     * @param category
     * @return
     */
    PageInfoVo getSongsByCategory(PageBo pageBo,int category);

    /**
     * 获取某个歌手的所有歌曲（分页）
     * @param SingerId
     * @return
     */
    PageInfoVo getSongsBySinger(PageBo pageBo,String SingerId);
}
