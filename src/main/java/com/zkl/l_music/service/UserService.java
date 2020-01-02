package com.zkl.l_music.service;

import com.zkl.l_music.Bo.UserBo;
import com.zkl.l_music.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    boolean addUser(UserBo userBo, HttpServletRequest request);

    boolean updateUser(UserEntity userEntity);

    boolean deleteUser(String id);

    UserEntity getUserById(String id);
}
