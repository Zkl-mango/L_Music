package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.AuthEntity;
import com.zkl.l_music.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthDao extends BaseMapper<AuthEntity> {

    /**
     * 获取授权信息(根据用户实体)
     * @param userEntity 用户实体
     * @return
     */
    public AuthEntity selectAuthByUser(UserEntity userEntity);
    /**
     * 获取授权信息(根据授权令牌)
     * @param token
     * @return
     */
    public AuthEntity selectAuthByToken(String token);
}
