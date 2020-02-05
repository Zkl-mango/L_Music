package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.AlbumDao;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.service.AlbumService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.AlbumDetailVo;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class AlbumServiceImpl implements AlbumService {

    @Resource
    AlbumDao albumDao;
    @Resource
    SingerDao singerDao;
    @Resource
    SongService songService;

    @Override
    public boolean addAlbum(AlbumEntity albumEntity) {
        int res = albumDao.insert(albumEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    //flag:-1,取消收藏;1,收藏；
    @Override
    public boolean updateAlbumByFlag(String id,int flag) {
        AlbumEntity albumEntity = albumDao.selectById(id);
        if(albumEntity==null) {
            return false;
        }
        albumEntity.setHot(albumEntity.getHot()+flag);
        int res = albumDao.updateById(albumEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAlbum(AlbumEntity albumEntity) {
        int res = albumDao.updateById(albumEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAlbum(String id) {
        int res = albumDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public AlbumDetailVo getAlbumById(String id) {
        AlbumEntity albumEntity = albumDao.selectById(id);
        AlbumDetailVo albumDetailVo = new AlbumDetailVo();
        BeanUtils.copyProperties(albumDetailVo,albumEntity);
        if(albumEntity == null) {
            return null;
        }
        List<SongVo> songVoList = songService.getSongsByAlbum(albumEntity.getId());
        albumDetailVo.setSongVoList(songVoList);
        return albumDetailVo;
    }

    @Override
    public AlbumEntity getAlbumEntityById(String id) {
        return albumDao.selectById(id);
    }

    /**
     * 根据歌手查找专辑
     * @param singerId
     * @return
     */
    @Override
    public List<AlbumEntity> getAlbumsBySinger(String singerId) {
        SingerEntity singerEntity = singerDao.selectById(singerId);
        if(singerEntity==null) {
            return null;
        }
        List<AlbumEntity> list = albumDao.selectAlbumsBySinger(singerId);
        return list;
    }

    /**
     * 查找最新的5个专辑
     * @return
     */
    @Override
    public List<AlbumEntity> getNewAlbums() {
        return albumDao.selectNewAlbums();
    }
}
