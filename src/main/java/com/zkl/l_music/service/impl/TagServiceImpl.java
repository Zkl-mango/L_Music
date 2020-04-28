package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.BigTagDao;
import com.zkl.l_music.dao.SmallTagDao;
import com.zkl.l_music.entity.BigTagEntity;
import com.zkl.l_music.entity.SmallTagEntity;
import com.zkl.l_music.service.TagService;
import com.zkl.l_music.vo.SmallTagVo;
import com.zkl.l_music.vo.TagVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    BigTagDao bigTagDao;
    @Resource
    SmallTagDao smallTagDao;

    @Override
    @Transactional
    public boolean addSmallTag(SmallTagEntity smallTagEntity) {
        smallTagDao.insert(smallTagEntity);
        return true;
    }

    @Override
    @Transactional
    public List<TagVo> getTagList() {
        List<BigTagEntity> bigTagList =  bigTagDao.selectAll();
        List<TagVo> categories = new ArrayList<>();
        for(int i=0;i<bigTagList.size();i++) {
            TagVo tagVo = new TagVo();
            tagVo.setName(bigTagList.get(i).getName());
            List<SmallTagEntity> smallTagList = smallTagDao.selectTagsByCategory(bigTagList.get(i).getId());
            List<SmallTagVo> smallTagVos = new ArrayList<>();
            for(int j=0; j<smallTagList.size();j++) {
               SmallTagVo smallTagVo = new SmallTagVo();
               smallTagVo.setInverted();
               smallTagVo.setType();
               smallTagVo.setName(smallTagList.get(j).getName());
               smallTagVos.add(smallTagVo);
            }
            tagVo.setSub(smallTagVos);
            categories.add(tagVo);
        }
        return categories;
    }

    @Override
    @Transactional
    public List<SmallTagEntity> getSmallTagByCat(int category) {
        return smallTagDao.selectTagsByCategory(category);
    }

    @Override
    @Transactional
    public List<SmallTagEntity> getSmallTagByHot(int hot) {
        return smallTagDao.selectTagsByHot(hot);
    }
}
