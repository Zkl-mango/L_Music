package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.RankListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankListDao extends BaseMapper<RankListEntity> {

    List<RankListEntity> selectRankListByRank(String rankId);

    List<RankListEntity> selectRankListByRecomment(String rankId,int recomment);
}
