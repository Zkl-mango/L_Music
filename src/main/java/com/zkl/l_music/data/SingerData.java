package com.zkl.l_music.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.service.SingerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("SingerData")
public class SingerData {

    @Resource
    SingerService singerService;

    public void getSingerData(String cat) {
        RestTemplate restTemplate=new RestTemplate();
        Map<String,Object> params=new HashMap<>();
        params.put("cat",cat);
        ResponseEntity<String> responseEntity=restTemplate.getForEntity
                (DataUrlConstant.url+"/artist/list?limit=100&cat={cat}",String.class,params);
        JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
        JSONArray jsonArray = jsonobj.getJSONArray("artists");
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            JSONArray aliasArray = jsonObject.getJSONArray("alias");
            String englishName = "";
            if(aliasArray.size()!=0) {
                englishName = aliasArray.getString(0);
                for(int j=1;j<aliasArray.size();j++) {
                    englishName += ","+aliasArray.getString(j);
                }
            }
            String sex="";
            if(cat.equals("1001")||cat.equals("2001")||cat.equals("6001")||cat.equals("7001")
            ||cat.equals("4001")){
                sex = "男";
            }
            if(cat.equals("1002")||cat.equals("2002")||cat.equals("6002")||cat.equals("7002")
                    ||cat.equals("4002")){
                sex = "女";
            }
            String picture = jsonObject.getString("img1v1Url");
            int songs = jsonObject.getInteger("musicSize");
            int albums = jsonObject.getInteger("albumSize");
            SingerEntity singerEntity = new SingerEntity();
            singerEntity.setId(id);
            singerEntity.setSinger(name);
            singerEntity.setEnglishName(englishName);
            singerEntity.setPicture(picture);
            singerEntity.setSex(sex);
            singerEntity.setSongs(songs);
            singerEntity.setAlbums(albums);
            singerEntity.setFans(0);
            singerEntity.setCategory(Integer.valueOf(cat));
            singerService.addSinger(singerEntity);
            log.info("new Singer ------"+singerEntity);
        }

    }

}
