package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.LikeListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeListDao extends BaseMapper<LikeListEntity> {

    List<LikeListEntity> selectLikeListByType(String userId,int type);

}
