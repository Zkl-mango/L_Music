package com.zkl.l_music.service.impl;

import com.zkl.l_music.bo.LoginBo;
import com.zkl.l_music.bo.UserBo;
import com.zkl.l_music.bo.UserPwdBo;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.dao.SongListDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.dto.FollowsDto;
import com.zkl.l_music.entity.AuthEntity;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.entity.SongListEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.AuthService;
import com.zkl.l_music.service.LikeListService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.*;
import com.zkl.l_music.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;
    @Resource
    SingerDao singerDao;
    @Resource
    SongListDao songListDao;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    AuthService authService;
    @Resource
    SongListService songListService;
    @Resource
    LikeListService likeListService;

    @Override
    @Transactional
    public UserEntity addUser(UserBo userBo) throws Exception {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userBo,userEntity);
        userEntity.setId(uuidGenerator.generateUUID());
        log.info("对用户密码进行加密处理-----");
        userEntity.setPassword(SecurityUtil.encryptPassword(userBo.getPassword()));
        log.info("密码加密处理完成-----");
        log.info("开始处理头像信息-----注册使用默认头像");
        String avatar = ConstantUtil.avatar;
        userEntity.setAvatar(avatar);
        log.info("处理头像信息结束-----");
        int res = userDao.insert(userEntity);
        //生成默认 我喜欢 歌单
        SongListEntity songListEntity = new SongListEntity();
        songListEntity.setId( uuidGenerator.generateUUID());
        songListEntity.setCategory(2);
        songListEntity.setListName("我喜欢");
        songListEntity.setUserId(userEntity);
        songListEntity.setCreateTime(new Date());
        songListDao.insert(songListEntity);
        if(res == 1) {
            return userEntity;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean updateUserAvatar(MultipartFile file,String id) {
        log.info("开始更新头像信息-----");
        String avatar = HandleAvatarUtil.save(file,1);
        UserEntity userEntity = userDao.selectById(id);
        if(userEntity == null) {
            return false;
        }
        userEntity.setAvatar(avatar);
        log.info("更新头像信息结束-----");
        userDao.updateById(userEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(UserBo userBo,String id) {
        UserEntity userEntity = userDao.selectById(id);
        if(userEntity==null) {
            return false;
        }
        BeanUtils.copyProperties(userBo,userEntity);
        int res = userDao.updateById(userEntity);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteUser(String id) {
        UserEntity userEntity = userDao.selectById(id);
        if(userEntity==null) {
            return false;
        }
        String followIds = userEntity.getFollow();
        if(!StringUtils.isBlank(followIds)) {
            List<String> followList = Arrays.asList(followIds.split(","));
            //对关注的歌手关注数进行调整
            for(int i=0;i<followList.size();i++) {
                SingerEntity singerEntity = singerDao.selectById(followList.get(i));
                singerEntity.setFans(singerEntity.getFans()-1);
                singerDao.updateById(singerEntity);
            }
        }
        int res = userDao.deleteById(id);
        if(res == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public UserVo getUserById(String id) {
        UserEntity userEntity =  userDao.selectById(id);
        if(userEntity==null) {
            return null;
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userEntity,userVo);
        if(!StringUtils.isBlank(userEntity.getFollow())) {
            List<String> followList = Arrays.asList(userEntity.getFollow().split(","));
            List<FollowsDto> follows = new ArrayList<>();
            for(int i=0;i<followList.size();i++) {
                SingerEntity singerEntity = singerDao.selectById(followList.get(i));
                FollowsDto followsDto = new FollowsDto();
                followsDto.setId(singerEntity.getId());
                followsDto.setName(singerEntity.getSinger());
                followsDto.setPicture(singerEntity.getPicture());
                followsDto.setFans(singerEntity.getFans());
                follows.add(followsDto);
            }
            userVo.setFollowsList(follows);
        }
        //查找自建歌单
        userVo.setSongListVos(songListService.getSongListByUser(userEntity.getId(),1));
        //查找收藏歌单
        userVo.setLikeListVos(likeListService.getLikeListByUser(userEntity.getId(),ConstantUtil.listType));
        return userVo;
    }

    @Override
    @Transactional
    public boolean updateUserPassword(UserPwdBo userPwdBo) {
        UserEntity userEntity = userDao.selectUserByPhoneAndName(userPwdBo.getPhone(),userPwdBo.getName());
        if(userEntity == null) {
            return false;
        }
        try {
            userEntity.setPassword(SecurityUtil.encryptPassword(userPwdBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDao.updateById(userEntity);
        return true;
    }

    @Override
    @Transactional
    public Map<String,Object> judgeLogin(HttpServletRequest request, LoginBo loginBo) {
        Map<String,Object> res = new HashMap<String,Object>();
        UserEntity userEntity = userDao.selectUserByPhone(loginBo.getPhone());
        if(userEntity==null) {
            res.put("message","手机号不存在，请重新输入");
            return res;
        }
        try {
            if(userEntity.getPassword().equals(SecurityUtil.encryptPassword(loginBo.getPassword()))){
                AuthEntity authEntity = authService.getAuth(userEntity);
                if(authEntity == null) {
                    authEntity = authService.addToken(userEntity);
                }
                res.put("message","登录成功");
                res.put("auth", authEntity);
                return res;
            }
            res.put("message","密码不正确，请重新输入");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    @Transactional
    public boolean judgeUserNameAndPhone(String name,int type,String id) {
        if(type == 0) {
            //判断是否有重复的手机号
            UserEntity isUser = userDao.selectUserByPhone(name);
            if(isUser !=null ) {
                return false;
            }
        }
        else {
            //id为空则为注册
            if(StringUtils.isBlank(id)) {
                //判断是否有重名的名字
                UserEntity isUser = userDao.selectUserByName(name);
                if (isUser != null) {
                    return false;
                }
            } else {
                //判断是否有重名的名字
                UserEntity isUser = userDao.selectUserByName(name);
                if(isUser !=null && !isUser.equals(id)) {
                    return false;
                }
            }
        }
        return true;
    }
}
