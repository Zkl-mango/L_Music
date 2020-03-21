package com.zkl.l_music.service.impl;

import com.zkl.l_music.dao.AuthDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.AuthEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.AuthService;
import com.zkl.l_music.util.TokenGenrator;
import com.zkl.l_music.util.UUIDGenerator;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    UserDao userDao;
    @Resource
    AuthDao authDao;
    @Resource
    UUIDGenerator uuidGenerator;

    @Override
    public AuthEntity addToken(UserEntity userEntity) {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setId(uuidGenerator.generateUUID());
        authEntity.setUser(userEntity);
        authEntity.setToken(TokenGenrator.doGen(32));
        authEntity.setTokenCreateTime(new Date());
        authDao.insert(authEntity);
        return authEntity;
    }

    @Override
    @Transactional
    public boolean veriflyToken(String userid, String token) {
        UserEntity userEntity = userDao.selectById(userid);
        AuthEntity auth = authDao.selectAuthByUser(userEntity);
        if(auth!=null && auth.getToken().equals(token)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean veriflyToken(String token) {
        AuthEntity auth =  new AuthEntity();
        if(auth!=null && authDao.selectAuthByToken(token)!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void refreshAuth(AuthEntity auth) {
        auth.setToken(TokenGenrator.doGen(32));
        auth.setTokenCreateTime(new Date());
        authDao.updateById(auth);
    }

    @Override
    public AuthEntity getAuth(UserEntity user) {
        return authDao.selectAuthByUser(user);
    }

    @Override
    public UserEntity getMemberByToken(String token) {
        return authDao.selectAuthByToken(token).getUser();
    }
}
