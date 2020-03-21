package com.zkl.l_music.bo;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserBo implements Serializable {

    @NotBlank(message = "name is null")
    private String name;

    private String password;
    private String phone;
    private String followName;
}
