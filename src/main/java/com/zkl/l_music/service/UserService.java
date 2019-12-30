package com.zkl.l_music.service;

import com.zkl.l_music.entity.UserEntity;

public interface UserService {

    boolean addUser(UserEntity userEntity);

    boolean updateUser(UserEntity userEntity);

    boolean deleteUser(String id);

    UserEntity getUserById(String id);
}
