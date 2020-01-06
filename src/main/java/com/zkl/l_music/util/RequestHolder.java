package com.zkl.l_music.util;

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
    public static String getUserRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        return (String)request.getAttribute("user");
    }

}
