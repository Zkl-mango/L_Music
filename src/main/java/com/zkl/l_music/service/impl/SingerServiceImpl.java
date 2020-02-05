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
import com.zkl.l_music.service.SingerService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.PageUtils;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SingerDetailVo;
import com.zkl.l_music.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class SingerServiceImpl implements SingerService {

    @Resource
    SingerDao singerDao;
    @Resource
    AlbumDao albumDao;
    @Resource
    SongService songService;

    @Override
    public boolean addSinger(SingerEntity singerEntity) {
        int res = singerDao.insert(singerEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    //flag:-1,取消关注;1,关注；
    @Override
    public boolean updateSinger(String id,int flag) {
        SingerEntity singerEntity = singerDao.selectById(id);
        singerEntity.setFans(singerEntity.getFans()+flag);
        int res = singerDao.updateById(singerEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSinger(String id) {
        int res = singerDao.deleteById(id) ;
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public SingerDetailVo getSingerById(String id,PageBo pageBo) {
        SingerEntity singerEntity = singerDao.selectById(id);
        if(singerEntity==null) {
            return null;
        }
        SingerDetailVo singerDetailVo = new SingerDetailVo();
        BeanUtils.copyProperties(singerDetailVo,singerEntity);
        //获取歌手相关的专辑
        List<AlbumEntity> albumList = albumDao.selectAlbumsBySinger(singerEntity.getId());
        singerDetailVo.setAlbumList(albumList);
        //获取歌手相关的歌曲
        PageInfoVo pageInfoVo = songService.getSongsBySinger(pageBo,singerEntity.getId());
        List<SongVo> songList = pageInfoVo.getList();
        singerDetailVo.setSongList(songList);
        return singerDetailVo;
    }

    @Override
    public PageInfoVo getSingers(PageBo pageBo) {
        Page page = new Page();
        page.setSize(pageBo.getSize());
        page.setCurrent(pageBo.getSize());
        IPage iPage = singerDao.selectSingerList(page,null);
        return PageUtils.generatePageVo(iPage);
    }

    @Override
    public PageInfoVo getSingersBySex(PageBo pageBo, String sex) {
        Page page = new Page();
        page.setSize(pageBo.getSize());
        page.setCurrent(pageBo.getSize());
        IPage iPage = singerDao.selectSingerList(page,sex);
        return PageUtils.generatePageVo(iPage);
    }

    @Override
    public List<SingerEntity> getSingerByCategory(int category) {
        return singerDao.selectSingerByCat(category);
    }
}
