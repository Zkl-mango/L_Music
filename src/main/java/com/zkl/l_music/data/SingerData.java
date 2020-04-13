package com.zkl.l_music.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.dao.SingerDao;
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

    private static String[] letter = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T",
    "U","V","W","X","Y","Z","-1","0"};

    @Resource
    SingerService singerService;
    @Resource
    SingerDao singerDao;

    public void getSingerData(String cat) {
        RestTemplate restTemplate=new RestTemplate();
        Map<String,Object> params=new HashMap<>();
        params.put("cat",cat);
        for(int z=0;z<letter.length;z++) {
            params.put("initial",letter[z]);
            ResponseEntity<String> responseEntity=restTemplate.getForEntity
                    (DataUrlConstant.url+"/artist/list?limit=100&cat={cat}&initial={initial}",String.class,params);
            JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
            JSONArray jsonArray = jsonobj.getJSONArray("artists");
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                SingerEntity singer = singerDao.selectById(id);
                if(singer != null) {
                    continue;
                }
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
                else if(cat.equals("1002")||cat.equals("2002")||cat.equals("6002")||cat.equals("7002")
                        ||cat.equals("4002")){
                    sex = "女";
                }else {
                    sex = "组合";
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
                //歌手简介
                params.put("id",id);
                String about= "";
                ResponseEntity<String> response=restTemplate.getForEntity
                        (DataUrlConstant.url+"/artist/desc?id={id}",String.class,params);
                JSONObject json=JSONObject.parseObject(response.getBody());
                String briefDesc = json.getString("briefDesc");
                about += briefDesc+"\n";
                singerEntity.setAbout(about);
                singerService.addSinger(singerEntity);
                log.info("new Singer ------"+singerEntity);
            }
        }

    }

}
