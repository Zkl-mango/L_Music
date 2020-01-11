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
import com.zkl.l_music.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
    public boolean deleteSong(String id) {
        int res = songDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public SongVo getSongById(String id) {
         SongEntity songEntity = songDao.selectById(id);
         if(songEntity == null) {
             return null;
         }
         SongVo songVo = this.songChangeVo(songEntity);
         return songVo;
    }

    @Override
    public List<SongVo> getSongsByAlbum(String albumId) {
        List<SongEntity> list = songDao.selectSongsByAlbum(albumId);
        List<SongVo> songVoList = this.songDetails(list);
        return songVoList;
    }

    @Override
    public PageInfoVo getSongsByCategory(PageBo pageBo,int category) {
        Page page = new Page(pageBo.getPage(),pageBo.getSize());
        IPage iPage = songDao.selectSongsByCategory(page,category);
        List<SongEntity> list = iPage.getRecords();
        List<SongVo> songVoList = this.songDetails(list);
        iPage.setRecords(songVoList);
        return PageUtils.generatePageVo(iPage);
    }

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

    private SongVo songChangeVo(SongEntity songEntity) {
        List<SingerEntity> singerList = new ArrayList<>();
        SongVo songVo = new SongVo();
        String[] str = songEntity.getSingerId().split(",");
        BeanUtils.copyProperties(songVo, songEntity);
        for (String singerId : str) {
            SingerEntity singerEntity = singerDao.selectById(singerId);
            singerList.add(singerEntity);
        }
        AlbumEntity albumEntity = albumDao.selectById(songEntity.getAlbumId());
        songVo.setAlbum(albumEntity);
        songVo.setSingerList(singerList);
        return songVo;
    }
}
