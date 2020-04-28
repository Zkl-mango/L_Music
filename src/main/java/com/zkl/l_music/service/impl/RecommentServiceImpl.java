package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.RecommentDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.RecommentEntity;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.service.RecommentService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.UUIDGenerator;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongListVo;
import com.zkl.l_music.vo.SongVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommentServiceImpl implements RecommentService {

    @Resource
    RecommentDao recommentDao;
    @Resource
    SongService songService;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    UserDao userDao;
    @Resource
    SongListService songListService;

    @Override
    @Transactional
    public boolean insertNorRecomment() {
        List<SongEntity> list = songService.getSongsByRecomment();
        for(int i=0;i<list.size();i++) {
            RecommentEntity recommentEntity = new RecommentEntity();
            recommentEntity.setLinkId(list.get(i).getId());
            recommentEntity.setLinkType(ConstantUtil.songType);
            recommentEntity.setType(1.0);
            recommentEntity.setId(uuidGenerator.generateUUID());
            recommentEntity.setUserId(userDao.selectById(1));
            recommentDao.insert(recommentEntity);
        }
        List<SongListVo> songListVos = songListService.getLikeSongList();
        for(int i=0;i<songListVos.size();i++) {
            RecommentEntity recommentEntity = new RecommentEntity();
            recommentEntity.setLinkId(songListVos.get(i).getId());
            recommentEntity.setType(1.0);
            recommentEntity.setLinkType(ConstantUtil.listType);
            recommentEntity.setId(uuidGenerator.generateUUID());
            recommentEntity.setUserId(userDao.selectById(1));
            recommentDao.insert(recommentEntity);
        }
        return true;
    }

    @Override
    @Transactional
    public List<SongListDetailVo> getRecommentsSong(String userId) {
        List<RecommentEntity> list = recommentDao.selectRecommentsByUser(userId,ConstantUtil.songType);
        List<SongListDetailVo> songListDetailVos = new ArrayList<>();
        for(int i= 0;i<list.size();i++) {
            SongListDetailVo songListDetailVo = new SongListDetailVo();
            songListDetailVo.setId(list.get(i).getId());
            songListDetailVo.setSongVo(songService.getSongById(list.get(i).getLinkId()));
            songListDetailVos.add(songListDetailVo);
        }
        return songListDetailVos;
    }

    @Override
    @Transactional
    public List<SongListVo> getRecommentsList(String userId) {
        List<RecommentEntity> list = recommentDao.selectRecommentsByUser(userId,ConstantUtil.listType);
        List<SongListVo> songListVos = new ArrayList<>();
        for(int i= 0;i<list.size();i++) {
            SongListVo songListVo = songListService.getSongListById(list.get(i).getLinkId(),userId);
            songListVos.add(songListVo);
        }
        return songListVos;
    }


}
