package com.zkl.l_music.service;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SongListVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongListService {

    String addSongList(String songListName, String id,int category);

    boolean updateSongList(SongListEntity songListEntity);

    boolean updateSongListPicture(String id, MultipartFile file);

    boolean updateSongListNum(String id,int flag,int type,String userId);

    boolean deleteSongList(String id);

    SongListVo getSongListById(String id,String userId);

    /**
     * 获取自定义和喜欢歌单的信息
     * @param userId
     * @param category
     * @return
     */
    List<SongListVo> getSongListByUser(String userId,int category);

    /**
     * 推荐收藏量多的7首
     * @return
     */
    List<SongListVo> getLikeSongList();

    /**
     * 推荐播放量多的6首
     * @return
     */
    List<SongListVo> getHotSongList();

    PageInfoVo getSongListByTag(PageBo pageBo,String tag);
}
