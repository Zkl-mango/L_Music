package com.zkl.l_music.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserPwdBo implements Serializable {

    @NotBlank(message = "phone is null")
    private String phone;
    @NotBlank(message = "password is null")
    private String password;
    @NotBlank(message = "configPwd is null")
    private String configPwd;

}
