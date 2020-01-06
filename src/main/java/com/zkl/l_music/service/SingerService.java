package com.zkl.l_music.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.vo.PageInfoVo;

public interface SingerService {

    boolean addSinger(SingerEntity singerEntity);

    boolean updateSinger(String id,int flag);

    boolean deleteSinger(String id);

    SingerEntity getSingerById(String id);

    PageInfoVo getSingers(Page page);

    PageInfoVo getSingersBySex(Page page,String sex);
}
