package com.zkl.l_music.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class HandleAvatarUtil {

    public static String save(MultipartFile file,int type) {
        String imageName = null;
        String filePathFinal = "";
        if (file != null) {
            //头像
            if(type == 1) {
                imageName = "avatar_" + System.currentTimeMillis() + ".png";
                String split[] = file.getOriginalFilename().split("\\.");
                filePathFinal = ConstantUtil.avatarUpLoadPath + imageName;
            } else if(type == 2) {//处理封面
                imageName = "list_pic_" + System.currentTimeMillis() + ".png";
                String split[] = file.getOriginalFilename().split("\\.");
                filePathFinal = ConstantUtil.listPicUpLoadPath + imageName;
            }
            File localFile = new File(filePathFinal);
            if (!localFile.exists()) {
                localFile.mkdirs();
            } else {
                boolean del = localFile.delete();
                if (del == true) {
                    File newLocalFile = new File(filePathFinal);
                }
            }
            try {
                file.transferTo(localFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageName;
    }

}
