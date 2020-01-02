package com.zkl.l_music.Bo;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserBo implements Serializable {

    private String name;
    private String password;
    private String avatar;
}
