package com.zkl.l_music.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SingerDetailVo;

public interface SingerService {

    boolean addSinger(SingerEntity singerEntity);

    boolean updateSinger(String id,int flag);

    boolean deleteSinger(String id);

    SingerDetailVo getSingerById(String id,PageBo pageBo);

    PageInfoVo getSingers(PageBo pageBo);

    PageInfoVo getSingersBySex(PageBo pageBo,String sex);
}
