package com.zkl.l_music.service;

import com.zkl.l_music.entity.AuthEntity;
import com.zkl.l_music.entity.UserEntity;

public interface AuthService {

    public AuthEntity addToken(UserEntity userEntity);
    /**
     * 根据用户ID和校验密匙进行身份校验 - 用于隐式登录
     * @param userid 用户ID
     * @param token 校验密匙
     * @return 校验结果
     */
    public boolean veriflyToken(String userid,String token);
    /**
     * 根据校验密匙进行身份校验
     * @param token 校验密匙
     * @return 校验结果
     */
    public boolean veriflyToken(String token);
    /**
     * 关键信息更改后刷新授权信息
     * @param auth 授权信息
     */
    public void refreshAuth(AuthEntity auth);
    /**
     * 从成员信息实体获取Auth实体
     * @param user 成员信息实体
     * @return Auth实体
     */
    public AuthEntity getAuth(UserEntity user);
    /**
     *
     * @param token
     * @return
     */
    public UserEntity getMemberByToken(String token);
}
