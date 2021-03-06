package com.zkl.l_music.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.dao.AlbumDao;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.SingerService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.PageUtils;
import com.zkl.l_music.util.SortByChinese;
import com.zkl.l_music.vo.*;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SingerServiceImpl implements SingerService {

    @Resource
    SingerDao singerDao;
    @Resource
    AlbumDao albumDao;
    @Resource
    UserDao userDao;
    @Resource
    SongService songService;

    @Override
    @Transactional
    public boolean addSinger(SingerEntity singerEntity) {
        int res = singerDao.insert(singerEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    //flag:-1,取消关注;1,关注；
    @Override
    @Transactional
    public boolean updateSinger(String id,int flag,String userId) {
        SingerEntity singerEntity = singerDao.selectById(id);
        singerEntity.setFans(singerEntity.getFans()+flag);
        UserEntity userEntity = userDao.selectById(userId);
        if(flag == 1) {
            if(StringUtils.isBlank(userEntity.getFollow())) {
                userEntity.setFollow(id);
            } else {
                userEntity.setFollow(userEntity.getFollow()+","+id);
            }
        } else if(flag == -1) {
            String follows = userEntity.getFollow()+",";
            String singerId = id+",";
            String newfollows = follows.replace(singerId,"");
            if(!StringUtils.isBlank(newfollows)) {
                if(newfollows.charAt(newfollows.length()-1) == ',') {
                    newfollows = newfollows.substring(0,newfollows.length()-1);
                }
            }
            userEntity.setFollow(newfollows);
        }
        userDao.updateById(userEntity);
        int res = singerDao.updateById(singerEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteSinger(String id) {
        int res = singerDao.deleteById(id) ;
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public SingerDetailVo getSingerById(String id,PageBo pageBo,int type) {
        SingerEntity singerEntity = singerDao.selectById(id);
        if(singerEntity==null) {
            return null;
        }
        SingerDetailVo singerDetailVo = new SingerDetailVo();
        BeanUtils.copyProperties(singerEntity,singerDetailVo);
        Page page = null;
        //歌曲页数增加
        if(type == 0) {
            page = new Page(1,pageBo.getSize());
        } else if(type == 1) {//专辑页数增加
            page = new Page(pageBo.getPage(),pageBo.getSize());
            pageBo.setPage(1);
        }
        //获取歌手相关的专辑
        IPage<AlbumEntity> albumList = albumDao.selectAlbumsBySinger(page,singerEntity.getId());
        singerDetailVo.setAlbumList(albumList.getRecords());
        //获取歌手相关的歌曲
        PageInfoVo pageInfoVo = songService.getSongsBySinger(pageBo,singerEntity.getId());
        List<SongVo> songList = pageInfoVo.getList();
        List<SongListDetailVo> songListDetailVos = new ArrayList<>();
        for(int j=0;j<songList.size();j++) {
            SongVo songVo = songList.get(j);
            SongListDetailVo songListDetailVo = new SongListDetailVo();
            songListDetailVo.setSongVo(songVo);
            songListDetailVos.add(songListDetailVo);
        }
        singerDetailVo.setSongList(songListDetailVos);
        return singerDetailVo;
    }

    @Override
    @Transactional
    public List<SingerListVo> getSingers(PageBo pageBo,String sex,int category,String userId) {
        if(sex.equals("全部")) {
            sex = null;
        }
        UserEntity user = null;
        if(!StringUtils.isBlank(userId)) {
            user = userDao.selectById(userId);
        }
        Page page = new Page(pageBo.getPage(),pageBo.getSize());
        IPage<SingerEntity> ipage = singerDao.selectSingerList(page,sex,category);

        List<SingerListVo> singerListVos = SortByChinese.sort(ipage.getRecords(),user);
        //热度
        List<SingerEntity> hotlist = singerDao.selectHotTop(sex,category);
        List<SingerVo> singerVos = new ArrayList<>();
        for (SingerEntity singerEntity : hotlist) {
            SingerVo singerVo = new SingerVo();
            BeanUtils.copyProperties(singerEntity,singerVo);
            singerVo.setFollow("+ 关注");
            if(user != null) {
                if(SortByChinese.isFollow(user,singerEntity.getId())) {
                    singerVo.setFollow("已关注");
                }
            }
            singerVos.add(singerVo);
        }
        singerListVos.get(0).setSingerVoList(singerVos);
        return singerListVos;
    }

    @Override
    @Transactional
    public List<SingerEntity> getSingerByCategory(int category) {
        return singerDao.selectSingerByCat(category);
    }

    @Override
    @Transactional
    public List<SongListDetailVo> getSingerSongs(String id, PageBo pageBo) {
        List<SongListDetailVo> songListDetailVos = new ArrayList<>();
        int page = pageBo.getPage();
        for(int i=1;i<=page;i++) {
            pageBo.setPage(i);
            PageInfoVo pageInfoVo = songService.getSongsBySinger(pageBo,id);
            List<SongVo> songList = pageInfoVo.getList();
            for(int j=0;j<songList.size();j++) {
                SongVo songVo = songList.get(j);
                SongListDetailVo songListDetailVo = new SongListDetailVo();
                songListDetailVo.setSongVo(songVo);
                songListDetailVos.add(songListDetailVo);
            }
        }
        return songListDetailVos;
    }

    @Override
    @Transactional
    public List<SingerVo> getSingersByName(String name,String userId) {
        List<SingerEntity> list = singerDao.selectSingerName(name);
        List<SingerVo> singerVos = new ArrayList<>();
        UserEntity user;
        if(StringUtils.isBlank(userId)) {
            user = null;
        } else {
            user = userDao.selectById(userId);
        }
        for (SingerEntity singerEntity : list) {
            SingerVo singerVo = new SingerVo();
            BeanUtils.copyProperties(singerEntity,singerVo);
            singerVo.setFollow("+ 关注");
            if(user != null) {
                if(SortByChinese.isFollow(user,singerEntity.getId())) {
                    singerVo.setFollow("已关注");
                }
            }
            singerVos.add(singerVo);
        }
        return singerVos;
    }
}
