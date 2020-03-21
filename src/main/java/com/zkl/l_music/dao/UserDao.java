package com.zkl.l_music.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkl.l_music.entity.UserEntity;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    UserEntity selectUserByPhone(String phone);

    UserEntity selectUserByName(String name);

    UserEntity selectUserByPhoneAndName(@Param("phone") String phone, @Param("name")String name);
}
