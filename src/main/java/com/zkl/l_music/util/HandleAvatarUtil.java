package com.zkl.l_music.util;

import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class HandleAvatarUtil {

    public static String save(String image, HttpServletRequest request, String path){
        String imageName;
//		String path = request.getSession().getServletContext().getRealPath("/certificate/");
//		String path = configureBean.getFileUploadPath();
        File file = new File(path);
        if(!file.exists()&& !file.isDirectory()) {
            file.mkdirs();
        }
        try {
            Base64 base = new Base64();
            byte[] decode = base.decode(image);
            imageName = "certificate_"+System.currentTimeMillis() + ".png";
            InputStream fin = new ByteArrayInputStream(decode);
            FileOutputStream fout = new FileOutputStream(path+imageName);
            byte[] b = new byte[1024];
            int length = 0;
            while((length = fin.read(b)) > 0){
                fout.write(b,0,length);
            }
            fin.close();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return imageName;
    }
}
