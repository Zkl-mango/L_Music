package com.zkl.l_music.service.impl;

import com.zkl.l_music.Bo.UserBo;
import com.zkl.l_music.config.AvatarConfig;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.HandleAvatarUtil;
import com.zkl.l_music.util.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;
    @Resource
    UUIDGenerator uuidGenerator;

    @Resource
    private AvatarConfig avatarConfig;

    @Override
    public boolean addUser(UserBo userBo, HttpServletRequest request) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userEntity,userBo);
        log.info(userEntity.toString());
        userEntity.setId(uuidGenerator.generateUUID());
        log.info("开始处理头像信息-----");
        String image = userBo.getAvatar();
        String avatar = HandleAvatarUtil.save(image, request, avatarConfig.getUploadPath());
        userEntity.setAvatar(avatar);
        log.info("处理头像信息结束-----");
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
