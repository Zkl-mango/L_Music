package com.zkl.l_music.util;


import com.baomidou.mybatisplus.extension.api.IErrorCode;

public enum ReturnCode{

    SUCCESS(0, "SUCCESS"),
    FAIL(-1, "请求处理异常"),
    PARAMS_ERROR(2, "参数不合法"),
    SIGN_ERROR(3, "验签失败"),
    SYSTEM_ERROR(4, "系统异常"),
    NO_PERMISSION(5, "无此权限"),
    NO_LOGIN(6, "请重新登录"),
    ;

    private final Integer code;
    private final String msg;


    ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
