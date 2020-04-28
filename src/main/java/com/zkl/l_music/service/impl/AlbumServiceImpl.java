package com.zkl.l_music.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.dao.AlbumDao;
import com.zkl.l_music.dao.LikeListDao;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.LikeListEntity;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.service.AlbumService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.AlbumDetailVo;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AlbumServiceImpl implements AlbumService {

    @Resource
    AlbumDao albumDao;
    @Resource
    SingerDao singerDao;
    @Resource
    UserDao userDao;
    @Resource
    SongService songService;
    @Resource
    LikeListDao likeListDao;
    @Resource
    UUIDGenerator uuidGenerator;


    @Transactional
    @Override
    public boolean addAlbum(AlbumEntity albumEntity) {
        int res = albumDao.insert(albumEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    //flag:-1,取消收藏;1,收藏；
    @Transactional
    @Override
    public boolean updateAlbumByFlag(String id,int flag,String userId) {
        AlbumEntity albumEntity = albumDao.selectById(id);
        if(albumEntity==null) {
            return false;
        }
        AlbumEntity updateAlbum = new AlbumEntity();
        updateAlbum.setId(albumEntity.getId());
        LikeListEntity likeListEntity = new LikeListEntity();
        if(flag == 1) {
            likeListEntity.setId(uuidGenerator.generateUUID());
            likeListEntity.setLinkId(albumEntity.getId());
            likeListEntity.setTime(new Date());
            likeListEntity.setType(ConstantUtil.albumType);
            likeListEntity.setUserId(userDao.selectById(userId));
            likeListDao.insert(likeListEntity);
        } else if (flag == -1) {
            likeListEntity = likeListDao.selectLikeListByUserAndLike(userId,albumEntity.getId());
            likeListDao.deleteById(likeListEntity.getId());
        }
        updateAlbum.setSongs(albumEntity.getSongs());
        updateAlbum.setHot(albumEntity.getHot()+flag);
        int res = albumDao.updateById(updateAlbum);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean updateAlbum(AlbumEntity albumEntity) {
        int res = albumDao.updateById(albumEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean deleteAlbum(String id) {
        int res = albumDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public AlbumDetailVo getAlbumById(String id,String userId) {
        AlbumEntity albumEntity = albumDao.selectById(id);
        AlbumDetailVo albumDetailVo = new AlbumDetailVo();
        BeanUtils.copyProperties(albumEntity,albumDetailVo);
        if(albumEntity == null) {
            return null;
        }
        if(StringUtils.isBlank(userId)) {
            albumDetailVo.setLike(0);
        } else {
            LikeListEntity likeListEntity = likeListDao.selectLikeListByUserAndLike(userId,albumEntity.getId());
            if(likeListEntity==null) {
                albumDetailVo.setLike(0);
            } else {
                albumDetailVo.setLike(1);
            }
        }
        List<SongVo> songVoList = songService.getSongsByAlbum(albumEntity.getId());
        List<SongListDetailVo> songListDetail = new ArrayList<>();
        for(int i=0;i<songVoList.size();i++) {
            SongListDetailVo songListDetailVo = new SongListDetailVo();
            songListDetailVo.setSongVo(songVoList.get(i));
            songListDetail.add(songListDetailVo);
        }
        albumDetailVo.setSongVoList(songListDetail);
        List<AlbumEntity> list = albumDao.selectNewAlbumsBySinger(id,albumEntity.getSingerId().getId());
        albumDetailVo.setMoreAlbums(list);
        return albumDetailVo;
    }

    @Transactional
    @Override
    public AlbumEntity getAlbumEntityById(String id) {
        return albumDao.selectById(id);
    }

    /**
     * 根据歌手查找专辑
     * @param singerId
     * @return
     */
    @Transactional
    @Override
    public List<AlbumEntity> getAlbumsBySinger(PageBo pageBo, String singerId) {
        SingerEntity singerEntity = singerDao.selectById(singerId);
        if(singerEntity==null) {
            return null;
        }
        Page page = new Page(pageBo.getPage(),pageBo.getSize());
        IPage<AlbumEntity> ipage = albumDao.selectAlbumsBySinger(page,singerId);
        List<AlbumEntity> list = ipage.getRecords();
        return list;
    }

    @Transactional
    @Override
    public List<AlbumEntity> getAllAlbumsBySinger(String singerId) {
        SingerEntity singerEntity = singerDao.selectById(singerId);
        if(singerEntity==null) {
            return null;
        }
        List<AlbumEntity> list = albumDao.selectAllAlbumsBySinger(singerId);
        return list;
    }

    /**
     * 查找最新的3个专辑，除了本专辑以外
     * @return
     */
    @Transactional
    @Override
    public List<AlbumEntity> getNewAlbumsBySinger(String id,String singerId) {
        return albumDao.selectNewAlbumsBySinger(id,singerId);
    }

    @Transactional
    @Override
    public List<AlbumEntity> getNewAlbum() {
        return albumDao.selectNewAlbum();
    }
}
