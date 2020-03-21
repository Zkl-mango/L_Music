package com.zkl.l_music.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

public class HandleAvatarUtil {

    public static String save(MultipartFile file) {
        String imageName = null;
        if (file != null) {
            imageName = "avatar_" + System.currentTimeMillis() + ".png";
            String split[] = file.getOriginalFilename().split("\\.");
            String filePathFinal = ConstantUtil.uploadPath + imageName;
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
