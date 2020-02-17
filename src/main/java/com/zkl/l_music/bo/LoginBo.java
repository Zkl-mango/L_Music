package com.zkl.l_music.bo;

import lombok.Data;
import sun.security.util.ManifestEntryVerifier;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginBo implements Serializable {

    @NotBlank(message = "name is null")
    private String name;
    @NotBlank(message = "password is null")
    private String password;
    @NotNull(message = "type is null")
    private  int type;      /*0:用户名密码登录，1:手机号验证码登录*/
}
