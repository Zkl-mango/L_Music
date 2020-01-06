package com.zkl.l_music.service;

import com.zkl.l_music.bo.UserBo;
import com.zkl.l_music.bo.UserPwdBo;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    /**
     * 注册用户
     * @param userBo
     * @param request
     * @return
     * @throws Exception
     */
    boolean addUser(UserBo userBo, HttpServletRequest request) throws Exception;

    boolean updateUser(UserBo userBo,String id,HttpServletRequest request);

    boolean deleteUser(String id);

    /**
     * 获取用户个人信息
     * @param id
     * @return
     */
    UserVo getUserById(String id);

    /**
     * 重置密码
     * @param userPwdBo
     * @return
     */
    boolean updateUserPassword(UserPwdBo userPwdBo);
}
