package com.zkl.l_music.service;

import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.RankListEntity;
import com.zkl.l_music.vo.RankDetailVo;

import java.util.List;
import java.util.Map;

public interface RankService {

    boolean addRank(RankEntity rankEntity);

    /**
     * 更新播放量
     * @param id
     * @return
     */
    boolean updateRank(String id);

    Map<Object,Object> getRankList();

    RankEntity getRankById(String id);

    /**
     * 获取榜单详情
     * @param id
     * @return
     */
    List<RankDetailVo> getRankListById(String id);
}
