package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class SongServiceImpl implements SongService {

    @Resource
    SongDao songDao;

    @Override
    public boolean addSong(SongEntity songEntity) {
        int res = songDao.insert(songEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    //hot,increase:1添加，-1取消，0不更改
    //recommend：1推荐，0不推荐
    @Override
    public boolean updateSong(String id,int hot,int recommend,int increase) {
        SongEntity songEntity = songDao.selectById(id);
        if(songEntity == null) {
            return false;
        }
        songEntity.setHot(songEntity.getHot()+hot);
        songEntity.setIncrease(songEntity.getIncrease()+increase);
        songEntity.setRecommend(recommend);
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
         songDao.selectById(id);
         return null;
    }

    @Override
    public List<SongVo> getSongsByAlbum(String albumId) {
        return null;
    }

    @Override
    public PageInfoVo getSongsByCategory(int category) {
        return null;
    }

    @Override
    public PageInfoVo getSongsBySinger(String SingerId) {
        return null;
    }
}
