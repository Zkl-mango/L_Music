package com.zkl.l_music.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

@Slf4j
public class ApiResponse <T> implements Serializable {

    /**响应编码：0成功；-1系统异常；*/
    private int code;
    /**响应结果描述*/
    private String message;
    /**业务数据*/
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 无业务数据的成功响应
     * @return
     */
    public static ApiResponse success() {
        return success(null);
    }

    /**
     * 带业务数据的成功响应
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResponse success(T data) {
        ApiResponse response = new ApiResponse();
        response.setCode(ReturnCode.SUCCESS.getCode());
        response.setMessage(ReturnCode.SUCCESS.getMsg());
        response.setData(data);
        log.info("请求返回报文为：{}", response.toString());
        return response;
    }

    /**
     * 响应失败
     * @return
     * @param res
     */
    public static ApiResponse fail(Map<String, Object> res) {
        return fail(ReturnCode.FAIL.getCode(), ReturnCode.FAIL.getMsg());
    }

    public static ApiResponse fail(String msg) {
        return fail(ReturnCode.FAIL.getCode(), msg);
    }

    /**
     * 响应失败
     * @param responseCode
     * @return
     */
    public static ApiResponse fail(ReturnCode responseCode) {
        return fail(responseCode.getCode(), responseCode.getMsg());
    }

    /**
     * 响应失败
     * @param failCode
     * @param msg
     * @return
     */
    public static ApiResponse fail(int failCode, String msg) {
        ApiResponse response = new ApiResponse();
        response.setCode(failCode);
        response.setMessage(msg);
        log.info("响应失败，返回报文为{}", response.toString());
        //设置响应头
        HttpServletResponse currentResponse = RequestHolder.getCurrentResponse();
        currentResponse.setStatus(getResponseStatus(failCode));
        return response;
    }

    private static Integer getResponseStatus(int failCode){
        if(failCode == ReturnCode.FAIL.getCode() || failCode ==ReturnCode.SYSTEM_ERROR.getCode()){
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }else if(failCode == ReturnCode.PARAMS_ERROR.getCode()){
            return HttpStatus.BAD_REQUEST.value();
        }else if(failCode == ReturnCode.SIGN_ERROR.getCode()|| failCode == ReturnCode.NO_PERMISSION.getCode()){
            return HttpStatus.UNAUTHORIZED.value();
        }else {
            return HttpStatus.OK.value();
        }
    }



}
