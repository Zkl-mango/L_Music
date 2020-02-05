package com.zkl.l_music.util;

import org.springframework.stereotype.Component;

@Component("AvatarConstant")
public class AvatarConstant {

    private String uploadPath="";

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}
