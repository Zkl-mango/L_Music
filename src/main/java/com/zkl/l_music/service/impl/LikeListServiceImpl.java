package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.LikeListDao;
import com.zkl.l_music.entity.LikeListEntity;
import com.zkl.l_music.service.LikeListService;
import com.zkl.l_music.service.SongDetailsService;
import com.zkl.l_music.vo.SongListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeListServiceImpl implements LikeListService {

    @Resource
    LikeListDao likeListDao;
    @Resource
    SongDetailsService songDetailsService;

    @Override
    public List<SongListVo> getSongListByUser(String userId, int type) {
        List<LikeListEntity> likeList =  likeListDao.selectLikeListByType(userId,type);
        List<SongListVo> songListVos = new ArrayList<>();
        for(int i=0;i<likeList.size();i++) {
            SongListVo songListVo = new SongListVo();
            BeanUtils.copyProperties(likeList.get(i),songListVo);
            songListVo.setSongNum(songDetailsService.countSongDetailsByList(likeList.get(i).getId()));
            songListVos.add(songListVo);
        }
        return songListVos;
    }
}
