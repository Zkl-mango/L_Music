package com.zkl.l_music.service.impl;

import com.zkl.l_music.bo.SongDetailBo;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.dao.SongDetailsDao;
import com.zkl.l_music.dao.SongListDao;
import com.zkl.l_music.entity.SongDetailsEntity;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SongDetailsServiceImpl implements SongDetailsService {

    @Resource
    SongDetailsDao songDetailsDao;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    SongDao songDao;
    @Resource
    SongListDao songListDao;
    @Resource
    SongService songService;

    @Override
    public boolean addSongDetails(SongDetailBo songDetailBo) {
        SongDetailsEntity songDetailsEntity  = songDetailsDao.selectSongDetailsBySongAndList(songDetailBo.getSongList(),songDetailBo.getSongId());
        if(songDetailsEntity != null) {
            return false;
        }
        songDetailsEntity = new SongDetailsEntity();
        songDetailsEntity.setId(uuidGenerator.generateUUID());
        songDetailsEntity.setDeleted(ConstantUtil.STATUS_SUCCESS);
        songDetailsEntity.setCreateAt(new Date());
        SongEntity songEntity = songDao.selectById(songDetailBo.getSongId());
        songDetailsEntity.setSongId(songEntity);
        SongListEntity songListEntity = songListDao.selectById(songDetailBo.getSongList());
        songDetailsEntity.setSongList(songListEntity);
        if(songListEntity.getListName().equals("我喜欢")) {
            songEntity.setLikeNum(songEntity.getLikeNum()+1);
            songDao.updateById(songEntity);
        }
        int res = songDetailsDao.insert(songDetailsEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    //删除某一首歌
    @Override
    public boolean updateSongDetails(String id) {
        SongDetailsEntity songDetailsEntity = songDetailsDao.selectById(id);
        if(songDetailsEntity == null) {
            return false;
        }
        songDetailsEntity.setDeleted(ConstantUtil.STATUS_DELETE);
        int res = songDetailsDao.updateById(songDetailsEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSongDetails(String id) {
        int res = songDetailsDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSongDetailsByList(String listId) {
        boolean res = songDetailsDao.deleteSongDetailsByListId(listId);
        return true;
    }

    @Override
    public SongDetailsEntity getSongDetailsById(String id) {
        return songDetailsDao.selectById(id);
    }

    /**
     * 根据歌单获取歌曲信息
     * @param listId
     * @return
     */
    @Override
    public List<SongListDetailVo> getSongDetailsByList(String listId) {
        List<SongDetailsEntity> list = songDetailsDao.selectSongDetailsByListId(listId);
        List<SongListDetailVo> listDetailVos = new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            SongListDetailVo songListDetailVo = new SongListDetailVo();
            BeanUtils.copyProperties(list.get(i),songListDetailVo);
            SongEntity songEntity = list.get(i).getSongId();
            SongVo songVo = songService.getSongById(songEntity.getId());
            songListDetailVo.setSongVo(songVo);
            listDetailVos.add(songListDetailVo);
        }
        return listDetailVos;
    }

    @Override
    public int countSongDetailsByList(String listId) {
        return songDetailsDao.countSongsByList(listId);
    }
}
