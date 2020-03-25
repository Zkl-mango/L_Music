package com.zkl.l_music.service;

import com.zkl.l_music.entity.BigTagEntity;
import com.zkl.l_music.entity.SmallTagEntity;
import com.zkl.l_music.vo.TagVo;

import java.util.List;

public interface TagService {

    boolean addSmallTag(SmallTagEntity smallTagEntity);

    List<TagVo> getTagList();

    List<SmallTagEntity> getSmallTagByCat(int category);

    List<SmallTagEntity> getSmallTagByHot(int hot);
}
