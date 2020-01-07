package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.AlbumEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlbumDao extends BaseMapper<AlbumEntity> {

    /**
     * 根据歌手查找专辑(时间排序)
     * @param singerId
     * @return
     */
    List<AlbumEntity> selectAlbumsBySinger(String singerId);

    /**
     * 查找最新的五个专辑
     * @return
     */
    List<AlbumEntity> selectNewAlbums();

}
