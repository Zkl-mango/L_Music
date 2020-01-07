package com.zkl.l_music.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.service.SingerService;
import com.zkl.l_music.util.PageUtils;
import com.zkl.l_music.vo.PageInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class SingerServiceImpl implements SingerService {

    @Resource
    SingerDao singerDao;

    @Override
    public boolean addSinger(SingerEntity singerEntity) {
        int res = singerDao.insert(singerEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    //flag:-1,取消关注;1,关注；
    @Override
    public boolean updateSinger(String id,int flag) {
        SingerEntity singerEntity = singerDao.selectById(id);
        singerEntity.setFans(singerEntity.getFans()+flag);
        int res = singerDao.updateById(singerEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSinger(String id) {
        int res = singerDao.deleteById(id) ;
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public SingerEntity getSingerById(String id) {
        return singerDao.selectById(id);
    }

    @Override
    public PageInfoVo getSingers(PageBo pageBo) {
        Page page = new Page();
        page.setSize(pageBo.getSize());
        page.setCurrent(pageBo.getSize());
        IPage iPage = singerDao.selectSingerList(page,null);
        return PageUtils.generatePageVo(iPage);
    }

    @Override
    public PageInfoVo getSingersBySex(PageBo pageBo, String sex) {
        Page page = new Page();
        page.setSize(pageBo.getSize());
        page.setCurrent(pageBo.getSize());
        IPage iPage = singerDao.selectSingerList(page,sex);
        return PageUtils.generatePageVo(iPage);
    }
}
