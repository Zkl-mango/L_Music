package com.zkl.l_music.service;

import com.zkl.l_music.bo.LoginBo;
import com.zkl.l_music.bo.UserBo;
import com.zkl.l_music.bo.UserPwdBo;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.UserVo;
import org.apache.catalina.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {

    /**
     * 注册用户
     * @param userBo
     * @return
     * @throws Exception
     */
    UserEntity addUser(UserBo userBo) throws Exception;

    boolean updateUserAvatar(MultipartFile file,String id);

    boolean updateUser(UserBo userBo,String id);

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

    Map<String,Object> judgeLogin(HttpServletRequest request, LoginBo loginBo);

    /**
     * 判断是否重名
     * @param name
     * @return
     */
    boolean judgeUserNameAndPhone(String name,int type,String id) ;

}
