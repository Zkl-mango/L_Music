package com.zkl.l_music.service;

import com.zkl.l_music.entity.RecommentEntity;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongListVo;

import java.util.List;

public interface RecommentService {

    boolean insertNorRecomment();

    boolean deletedAll();

    List<SongListDetailVo> getRecommentsSong(String userId);

    List<SongListVo> getRecommentsList(String userId);
}
