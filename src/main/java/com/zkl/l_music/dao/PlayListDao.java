package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.PlayListEntity;
import com.zkl.l_music.vo.PlayListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayListDao extends BaseMapper<PlayListEntity> {

    int insertPlayList(PlayListVo playListVo);

    int deletedByUser(String userId);

    List<PlayListEntity> selectPlaysByUser(String userId);
}
