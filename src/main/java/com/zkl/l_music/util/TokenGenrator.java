package com.zkl.l_music.util;

import java.util.Random;

public class TokenGenrator {

    /**
     * 权限密匙生成方法
     * @param length 密匙长度
     * @return 生成的权限密匙
     */
    public static String doGen(int length) {
        final int maxNum = 62;
        int i;
        int count = 0;
        char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ,
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        StringBuffer token = new StringBuffer("");
        Random r = new Random();
        while (count < length) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                token.append(str[i]);
                count++;
            }
        }
        return token.toString();
    }
}
