package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.entity.AlbumEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlbumDao extends BaseMapper<AlbumEntity> {

    /**
     * 根据歌手查找专辑(时间排序),分页
     * @param singerId
     * @return
     */
    IPage<AlbumEntity> selectAlbumsBySinger(Page page, String singerId);

    List<AlbumEntity> selectAllAlbumsBySinger(String singerId);
    /**
     * 查找最新的3个专辑，除了本专辑以外
     * @return
     */
    List<AlbumEntity> selectNewAlbums(String id,String singerId);

}
