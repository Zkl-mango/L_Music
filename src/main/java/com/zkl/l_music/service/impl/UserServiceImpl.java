package com.zkl.l_music.service.impl;

import com.zkl.l_music.bo.LoginBo;
import com.zkl.l_music.bo.UserBo;
import com.zkl.l_music.bo.UserPwdBo;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.dto.FollowsDto;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.*;
import com.zkl.l_music.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;
    @Resource
    SingerDao singerDao;
    @Resource
    UUIDGenerator uuidGenerator;

    @Resource
    private AvatarConstant avatarConstant;

    @Override
    public boolean addUser(UserBo userBo, HttpServletRequest request) throws Exception {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userEntity,userBo);
        userEntity.setId(uuidGenerator.generateUUID());
        log.info("对用户密码进行加密处理-----");
        userEntity.setPassword(SecurityUtil.encryptPassword(userBo.getPassword()));
        log.info("密码加密处理完成-----");
        log.info("开始处理头像信息-----");
        String image = userBo.getAvatar();
        String avatar = ConstantUtil.avatar;
        if(!StringUtils.isBlank(image)) {
            avatar = HandleAvatarUtil.save(image, request, avatarConstant.getUploadPath());
        }
        userEntity.setAvatar(avatar);
        log.info("处理头像信息结束-----");
        int res = userDao.insert(userEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(UserBo userBo,String id,HttpServletRequest request) {
        UserEntity userEntity = userDao.selectById(id);
        if(userEntity==null) {
            return false;
        }
        BeanUtils.copyProperties(userEntity,userBo);
        //如果头像发生更改
        if(userBo.getAvatar()!=null) {
            log.info("开始更新头像信息-----");
            String image = userBo.getAvatar();
            String avatar = HandleAvatarUtil.save(image, request, avatarConstant.getUploadPath());
            userEntity.setAvatar(avatar);
            log.info("更新头像信息结束-----");
        }
        int res = userDao.updateById(userEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(String id) {
        UserEntity userEntity = userDao.selectById(id);
        if(userEntity==null) {
            return false;
        }
        String followIds = userEntity.getFollow();
        List<String> followList = Arrays.asList(followIds.split(","));
        //对关注的歌手关注数进行调整
        for(int i=0;i<followList.size();i++) {
            SingerEntity singerEntity = singerDao.selectById(followList.get(i));
            singerEntity.setFans(singerEntity.getFans()-1);
            singerDao.updateById(singerEntity);
        }
        int res = userDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    public UserVo getUserById(String id) {
        UserEntity userEntity =  userDao.selectById(id);
        if(userEntity==null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userVo,userEntity);
        List<String> followList = Arrays.asList(userEntity.getFollow().split(","));
        List<FollowsDto> follows = new ArrayList<>();
        for(int i=0;i<followList.size();i++) {
            SingerEntity singerEntity = singerDao.selectById(followList.get(i));
            FollowsDto followsDto = new FollowsDto();
            followsDto.setId(singerEntity.getId());
            followsDto.setName(singerEntity.getSinger());
            follows.add(followsDto);
        }
        userVo.setFollowsList(follows);
        return userVo;
    }

    @Override
    public boolean updateUserPassword(UserPwdBo userPwdBo) {
        if(!userPwdBo.getPassword().equals(userPwdBo.getConfigPwd())) {
            return false;
        }
        UserEntity userEntity = userDao.selectUserByPhone(userPwdBo.getPhone());
        try {
            userEntity.setPassword(SecurityUtil.encryptPassword(userPwdBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDao.updateById(userEntity);
        return true;
    }

    @Override
    public String judgeLogin(LoginBo loginBo) {
        UserEntity userEntity = new UserEntity();
        if(loginBo.getType()==0) {
            userEntity = userDao.selectUserByName(loginBo.getName());
            if(userEntity==null) {
                return "用户名错误，请重新输入";
            }
            try {
                if(userEntity.getPassword().equals(SecurityUtil.encryptPassword(loginBo.getPassword()))){
                    RequestHolder.setUserRequest(userEntity);
                    return "登录成功";
                }
                return "密码不正确，请重新输入";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
