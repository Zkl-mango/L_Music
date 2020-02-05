package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.vo.SingerListVo;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface SingerDao extends BaseMapper<SingerEntity> {

    /**
     * 根据性别查找歌手
     * @param sex
     * @return
     */
    IPage<SingerListVo> selectSingerList(Page page,String sex);

    List<SingerEntity> selectSingerByCat(int category);

}
