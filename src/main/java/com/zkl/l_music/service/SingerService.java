package com.zkl.l_music.service;

import com.zkl.l_music.entity.SingerEntity;

public interface SingerService {

    boolean addSinger(SingerEntity singerEntity);

    boolean updateSinger(SingerEntity singerEntity);

    boolean deleteSinger(String id);

    SingerEntity getSingerById(String id);

}
