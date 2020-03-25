package com.zkl.l_music.util;

import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.vo.SingerListVo;
import com.zkl.l_music.vo.SingerVo;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class SortByChinese {

    private static String[] letter = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",
                "U","V","W","X","Y","Z"};

    public static List sort(List list,UserEntity user) {
        return sortByChinese(list,user);
    }

    /**
     * 初始化列表
     * @return
     */
    private static List<SingerListVo> createList() {
        List<SingerListVo> result = new ArrayList<>();
        SingerListVo first = new SingerListVo();
        first.setLetter("a");
        first.setText("热");
        first.setSingerVoList(new ArrayList<>());
        result.add(first);
        for(int i=1;i<27;i++) {
            SingerListVo singerListVo = new SingerListVo();
            singerListVo.setLetter(letter[i-1]);
            singerListVo.setText(letter[i-1]);
            List<SingerVo> list = new ArrayList<>();
            singerListVo.setSingerVoList(list);
            result.add(singerListVo);
        }
        SingerListVo last = new SingerListVo();
        last.setLetter("_");
        last.setText("#");
        last.setSingerVoList(new ArrayList<>());
        result.add(last);
        return result;
    }

    private static void caseLetter(String letter,SingerVo singerVo,List<SingerListVo> result) {
        switch (letter) {
            case "a":
                result.get(1).getSingerVoList().add(singerVo);
                break;
            case "b":
                result.get(2).getSingerVoList().add(singerVo);
                break;
            case "c":
                result.get(3).getSingerVoList().add(singerVo);
                break;
            case "d":
                result.get(4).getSingerVoList().add(singerVo);
                break;
            case "e":
                result.get(5).getSingerVoList().add(singerVo);
                break;
            case "f":
                result.get(6).getSingerVoList().add(singerVo);
                break;
            case "g":
                result.get(7).getSingerVoList().add(singerVo);
                break;
            case "h":
                result.get(8).getSingerVoList().add(singerVo);
                break;
            case "i":
                result.get(9).getSingerVoList().add(singerVo);
                break;
            case "j":
                result.get(10).getSingerVoList().add(singerVo);
                break;
            case "k":
                result.get(11).getSingerVoList().add(singerVo);
                break;
            case "l":
                result.get(12).getSingerVoList().add(singerVo);
                break;
            case "n":
                result.get(13).getSingerVoList().add(singerVo);
                break;
            case "m":
                result.get(14).getSingerVoList().add(singerVo);
                break;
            case "o":
                result.get(15).getSingerVoList().add(singerVo);
                break;
            case "p":
                result.get(16).getSingerVoList().add(singerVo);
                break;
            case "q":
                result.get(17).getSingerVoList().add(singerVo);
                break;
            case "r":
                result.get(18).getSingerVoList().add(singerVo);
                break;
            case "s":
                result.get(19).getSingerVoList().add(singerVo);
                break;
            case "t":
                result.get(20).getSingerVoList().add(singerVo);
                break;
            case "u":
                result.get(21).getSingerVoList().add(singerVo);
                break;
            case "v":
                result.get(22).getSingerVoList().add(singerVo);
                break;
            case "w":
                result.get(23).getSingerVoList().add(singerVo);
                break;
            case "x":
                result.get(24).getSingerVoList().add(singerVo);
                break;
            case "y":
                result.get(25).getSingerVoList().add(singerVo);
                break;
            case "z":
                result.get(26).getSingerVoList().add(singerVo);
                break;
            default:
                result.get(27).getSingerVoList().add(singerVo);
                break;
        }
    }
    /**
     * 看是否是汉字，是汉字进行匹配
     * 看是否是英文，是英文直接转成小写加进去。
     * 其他则直接加
     * @param list
     * @return
     */
    private static List sortByChinese(List<SingerEntity> list,UserEntity userEntity) {
        List<SingerListVo> result = createList();
        for (SingerEntity singerEntity : list) {
            char[] chars = singerEntity.getSinger().toCharArray();
            SingerVo singerVo = new SingerVo();
            BeanUtils.copyProperties(singerEntity,singerVo);
            singerVo.setFollow("+ 关注");
            if(userEntity != null) {
                if(isFollow(userEntity,singerEntity.getId())) {
                    singerVo.setFollow("已关注");
                }
            }
            if (Character.toString(chars[0]).matches("[\\u4E00-\\u9FA5]+")) {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(chars[0]);
                if(pinyinArray == null) {
                    caseLetter(Character.toString(chars[0]),singerVo,result);
                } else {
                    caseLetter(Character.toString(pinyinArray[0].charAt(0)),singerVo,result);
                }
            } else if (Character.toString(chars[0]).matches("[a-zA-Z]+")) {
                caseLetter(Character.toString(chars[0]).toLowerCase(),singerVo,result);
            } else {
                caseLetter(Character.toString(chars[0]),singerVo,result);
            }
        }
        return result;
    }


    public static boolean isFollow(UserEntity user,String singerId) {
        String followId = user.getFollow();
        if(StringUtils.isBlank(followId)) {
            return false;
        }
        if(followId.contains(singerId)) {
            return true;
        }
        return false;
    }
}
