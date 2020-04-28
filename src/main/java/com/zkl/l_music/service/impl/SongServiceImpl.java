package com.zkl.l_music.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.dao.AlbumDao;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.PageUtils;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SongServiceImpl implements SongService {

    @Resource
    SongDao songDao;
    @Resource
    AlbumDao albumDao;
    @Resource
    SingerDao singerDao;

    @Override
    @Transactional
    public boolean addSong(SongEntity songEntity) {
        int res = songDao.insert(songEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    //hot,increase:1添加，-1取消，0不更改
    //recommend：1推荐，0不推荐,-1不修改
    @Override
    @Transactional
    public boolean updateSong(String id,int hot,int recommend) {
        SongEntity songEntity = songDao.selectById(id);
        if(songEntity == null) {
            return false;
        }
        songEntity.setHot(songEntity.getHot()+hot);
        if(recommend != -1) {
            songEntity.setRecommend(recommend);
        }
        int res = songDao.updateById(songEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteSong(String id) {
        int res = songDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public SongVo getSongById(String id) {
         SongEntity songEntity = songDao.selectById(id);
         if(songEntity == null) {
             return null;
         }
         SongVo songVo = this.songChangeVo(songEntity);
         return songVo;
    }

    @Override
    @Transactional
    public List<SongVo> getSongsByAlbum(String albumId) {
        List<SongEntity> list = songDao.selectSongsByAlbum(albumId);
        List<SongVo> songVoList = this.songDetails(list);
        return songVoList;
    }

    @Override
    @Transactional
    public PageInfoVo getSongsByCategory(PageBo pageBo,int category) {
        Page page = new Page(pageBo.getPage(),pageBo.getSize());
        IPage iPage = songDao.selectSongsByCategory(page,category);
        List<SongEntity> list = iPage.getRecords();
        List<SongVo> songVoList = this.songDetails(list);
        iPage.setRecords(songVoList);
        return PageUtils.generatePageVo(iPage);
    }

    @Transactional
    @Override
    public PageInfoVo getSongsBySinger(PageBo pageBo,String singerId) {
        Page page = new Page(pageBo.getPage(),pageBo.getSize());
        IPage iPage = songDao.selectSongsBySinger(page,singerId);
        List<SongEntity> list = iPage.getRecords();
        List<SongVo> songVoList = this.songDetails(list);
        iPage.setRecords(songVoList);
        return PageUtils.generatePageVo(iPage);
    }

    //获取歌曲详细信息，转换成对应的VO
    private List<SongVo> songDetails(List<SongEntity> list) {
        List<SongVo> songVoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SongVo songVo = this.songChangeVo(list.get(i));
            songVoList.add(songVo);
        }
        return songVoList;
    }

    public SongVo songChangeVo(SongEntity songEntity) {
        List<SingerEntity> singerList = new ArrayList<>();
        SongVo songVo = new SongVo();
        String[] str = songEntity.getSingerId().split(",");
        BeanUtils.copyProperties(songEntity,songVo);
        for (String singerId : str) {
            SingerEntity singerEntity = singerDao.selectById(singerId);
            singerList.add(singerEntity);
        }
        AlbumEntity albumEntity = albumDao.selectById(songEntity.getAlbumId());
        songVo.setAlbum(albumEntity);
        songVo.setSingerList(singerList);
        return songVo;
    }

    @Override
    @Transactional
    public List<SongListDetailVo> getSongsByHot() {
        List<SongEntity> list = songDao.selectSongsByHot();
        List<SongVo> songDetails = this.songDetails(list);
        List<SongListDetailVo> songListDetail = new ArrayList<>();
        for(int i=0;i<songDetails.size();i++) {
            SongListDetailVo songListDetailVo = new SongListDetailVo();
            songListDetailVo.setSongVo(songDetails.get(i));
            songListDetail.add(songListDetailVo);
        }
        return songListDetail;
    }

    @Override
    @Transactional
    public List<SongEntity> getSongsByRecomment() {
        List<SongEntity> list = songDao.selectSongsByRecomment();
        return list;
    }
}
