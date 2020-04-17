package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.RankListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankDao extends BaseMapper<RankEntity> {

    List<RankEntity> selectRankByType(int type);

    List<RankEntity> selectRankByRecomment(int recomment);

    List<RankEntity> selectAllRank();
}
