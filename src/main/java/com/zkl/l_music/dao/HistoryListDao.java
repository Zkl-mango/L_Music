package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.HistoryListEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryListDao extends BaseMapper<HistoryListEntity> {

    int deletedHistorys(String userId,int type);

    List<HistoryListEntity> selectHistorysByUser(String userId,int type);

    HistoryListEntity selectHistoryByUserAndSong(String userId,String linkId,int type);
}
