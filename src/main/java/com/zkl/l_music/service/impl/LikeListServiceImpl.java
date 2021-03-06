package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.AlbumDao;
import com.zkl.l_music.dao.LikeListDao;
import com.zkl.l_music.dao.SongListDao;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.LikeListEntity;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.service.LikeListService;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.vo.AlbumVo;
import com.zkl.l_music.vo.SongListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeListServiceImpl implements LikeListService {

    @Resource
    LikeListDao likeListDao;
    @Resource
    SongListDao songListDao;
    @Resource
    AlbumDao albumDao;
    @Resource
    SongDetailsService songDetailsService;

    @Transactional
    @Override
    public boolean deleteLikeList(String id,int type) {
        LikeListEntity likeListEntity = likeListDao.selectById(id);
        if(likeListEntity == null) {
            return false;
        }
        //收藏数-1
        if(type == ConstantUtil.albumType) {
            AlbumEntity albumEntity = albumDao.selectById(likeListEntity.getLinkId());
            albumEntity.setHot(albumEntity.getHot()-1);
            albumEntity.setSingerId(null);
            albumDao.updateById(albumEntity);
        }
        if(type == ConstantUtil.listType) {
            SongListEntity songListEntity = songListDao.selectById(likeListEntity.getLinkId());
            songListEntity.setLikeNum(songListEntity.getLikeNum()-1);
            songListEntity.setUserId(null);
            songListDao.updateById(songListEntity);
        }
        int res = likeListDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<SongListVo> getLikeListByUser(String userId, int type) {
        List<LikeListEntity> likeList =  likeListDao.selectLikeListByType(userId,type);
        List<SongListVo> songListVos = new ArrayList<>();
        if(type == ConstantUtil.listType) {//喜欢的歌单
            for(int i=0;i<likeList.size();i++) {
                SongListEntity songListEntity = songListDao.selectById(likeList.get(i).getLinkId());
                SongListVo songListVo = new SongListVo();
                BeanUtils.copyProperties(songListEntity,songListVo);
                songListVo.setSongNum(songDetailsService.countSongDetailsByList(songListEntity.getId()));
                songListVo.setId(likeList.get(i).getId());
                songListVo.setListId(songListEntity.getId());
                songListVos.add(songListVo);
            }
        }
        return songListVos;
    }

    @Transactional
    @Override
    public List<AlbumVo> getLikeListAlbumByUser(String userId, int type) {
        List<LikeListEntity> likeList =  likeListDao.selectLikeListByType(userId,type);
        List<AlbumVo> songListVos = new ArrayList<>();
        if(type == ConstantUtil.albumType) {//喜欢的专辑
            for(int i=0;i<likeList.size();i++) {
                AlbumEntity albumEntity = albumDao.selectById(likeList.get(i).getLinkId());
                AlbumVo albumVo = new AlbumVo();
                BeanUtils.copyProperties(albumEntity,albumVo);
                albumVo.setSongNum(albumEntity.getSongs());
                albumVo.setListName(albumEntity.getName());
                albumVo.setId(likeList.get(i).getId());
                albumVo.setAlbumId(albumEntity.getId());
                songListVos.add(albumVo);
            }
        }
        return songListVos;
    }
}
