package com.zkl.l_music.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SingerDetailVo;
import com.zkl.l_music.vo.SingerListVo;

import java.util.List;

public interface SingerService {

    boolean addSinger(SingerEntity singerEntity);

    boolean updateSinger(String id,int flag,String userId);

    boolean deleteSinger(String id);

    SingerDetailVo getSingerById(String id,PageBo pageBo,int type);

    List<SingerListVo> getSingers(String sex,int category,String userId);

    List<SingerEntity> getSingerByCategory(int category);
}
