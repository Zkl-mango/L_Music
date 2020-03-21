package com.zkl.l_music.util;

import com.zkl.l_music.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RequestHolder {

    /**
     * 获取当前请求
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
    }

    /**
     * 获取当前线程响应
     * @return
     */
    public static HttpServletResponse getCurrentResponse(){
        return ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
    }

    /**
     * 获取当前请求用户的信息
     * @return
     */
    public static UserEntity getUserEntityRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        return (UserEntity) request.getSession().getAttribute("userEntity");
    }

    /**
     * 获取当前请求用户id
     * @return
     */
    public static String getUserRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        return (String)request.getSession().getAttribute("userId");
    }

    /**
     * 添加当前请求用户
     * @return
     */
    public static void setUserRequest(HttpServletRequest request,UserEntity userEntity) {
        request.getSession().setAttribute("userIds",userEntity.getId());
        request.getSession().setAttribute("userEntitys",userEntity);
    }
}
