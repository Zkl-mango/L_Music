package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.SongListDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.HandleAvatarUtil;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SongListServiceImpl implements SongListService {

    @Resource
    SongListDao songListDao;
    @Resource
    UserDao userDao;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    SongDetailsService songDetailsService;

    //添加歌单
    @Override
    public String addSongList(String songListName, String id,int category) {
        SongListEntity songListEntity = new SongListEntity();
        songListEntity.setListName(songListName);
        songListEntity.setId(uuidGenerator.generateUUID());
        songListEntity.setUserId(userDao.selectById(id));
        songListEntity.setPicture(ConstantUtil.listPicUpLoadPath);
        songListEntity.setCategory(category);
        int res = songListDao.insert(songListEntity);
        if(res == 1) {
            return songListEntity.getId();
        }
        return null;
    }

    //修改歌单信息
    @Override
    public boolean updateSongList(SongListEntity songListEntity) {
        SongListEntity songList = songListDao.selectById(songListEntity.getId());
        if(songList==null) {
            return false;
        }
        if(songListEntity.getPlayNum() == 0) {
            songListEntity.setPlayNum(songList.getPlayNum());
        }
        if(songList.getLikeNum() == 0) {
            songListEntity.setLikeNum(songList.getLikeNum());
        }
        songListEntity.setCategory(songList.getCategory());
        int res = songListDao.updateById(songListEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSongListPicture(String id, MultipartFile file) {
        log.info("开始更新歌曲封面-----");
        String picture = HandleAvatarUtil.save(file,2);
        SongListEntity songListEntity = songListDao.selectById(id);
        if(songListEntity == null) {
            return false;
        }
        songListEntity.setPicture(picture);
        log.info("更新歌曲封面结束-----");
        songListDao.updateById(songListEntity);
        return true;
    }

    //删除歌单
    @Override
    public boolean deleteSongList(String id) {
        int res = songListDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    //通过歌单id获取歌单详情
    @Override
    public SongListVo getSongListById(String id) {
        SongListEntity songListEntity = songListDao.selectById(id);
        if(songListEntity == null) {
            return null;
        }
        SongListVo songListVo = new SongListVo();
        BeanUtils.copyProperties(songListEntity,songListVo);
        return songListVo;
    }

    //获取用户歌单
    @Override
    public List<SongListVo> getSongListByUser(String userId,int category) {
        List<SongListEntity> songList =  songListDao.selectSongListByUser(userId,category);
        List<SongListVo> songListVos = new ArrayList<>();
        for(int i=0;i<songList.size();i++) {
            SongListVo songListVo = new SongListVo();
            BeanUtils.copyProperties(songList.get(i),songListVo);
            songListVo.setSongNum(songDetailsService.countSongDetailsByList(songList.get(i).getId()));
            songListVos.add(songListVo);
        }
        return songListVos;
    }
}
