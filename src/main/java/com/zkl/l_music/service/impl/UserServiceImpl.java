package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;


    @Override
    public boolean addUser(UserEntity userEntity) {
        int res = userDao.insert(userEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(UserEntity userEntity) {
        int res = userDao.updateById(userEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String id) {
        int res = userDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public UserEntity getUserById(String id) {
        return userDao.selectById(id);
    }
}
