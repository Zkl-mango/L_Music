package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    UserEntity selectUserByPhone(String phone);
}
