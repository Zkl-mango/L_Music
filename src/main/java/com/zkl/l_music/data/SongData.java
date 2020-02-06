package com.zkl.l_music.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.entity.SongEntity;
import com.zkl.l_music.service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("SongData")
public class SongData {

    @Resource
    SongService songService;

    //专辑获取歌曲信息
    public void getSongData(String albumid) {
        RestTemplate restTemplate=new RestTemplate();
        Map<String,Object> params=new HashMap<>();
        params.put("id",albumid);
        ResponseEntity<String> responseEntity=restTemplate.getForEntity
                (DataUrlConstant.url+"/album?id={id}",String.class,params);
        JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
        JSONArray jsonArray = jsonobj.getJSONArray("songs");
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            JSONArray singerArray = jsonObject.getJSONArray("ar");
            String singerId = singerArray.getJSONObject(0).getString("id");
            for(int j=1;j<singerArray.size();j++) {
                singerId +=","+ singerArray.getJSONObject(j).getString("id");
            }
            params.put("song_id",id);
            //调用api获取歌曲连接
            ResponseEntity<String> responseEntity_url=restTemplate.getForEntity
                    (DataUrlConstant.url+"/song/url?id={song_id}",String.class,params);
            JSONObject json_url=JSONObject.parseObject(responseEntity_url.getBody());
            JSONArray urlArray = json_url.getJSONArray("data");
            String url = urlArray.getJSONObject(0).getString("url");

            //调用api获取歌词
            ResponseEntity<String> responseEntity_lyric=restTemplate.getForEntity
                    (DataUrlConstant.url+"/lyric?id={song_id}",String.class,params);
            JSONObject json_lyric=JSONObject.parseObject(responseEntity_lyric.getBody());
            JSONObject lyricArray1 = json_lyric.getJSONObject("lrc");
            String lyric = "";
            String klyric = "";
            if(lyricArray1==null) {
                lyric = "找不到歌词信息";
            } else {
                lyric = lyricArray1.getString("lyric");
                JSONObject lyricArray2 = json_lyric.getJSONObject("klyric");
                klyric = lyricArray2.getString("lyric");
                if(klyric==null){
                    klyric="";
                }
            }
            SongEntity songEntity = new SongEntity();
            songEntity.setId(id);
            songEntity.setRecommend(0);
            songEntity.setHot(0);
            songEntity.setAlbumId(albumid);
            songEntity.setName(name);
            songEntity.setSingerId(singerId);
            songEntity.setLink(url);
            songEntity.setLyric(lyric);
            songEntity.setKlyric(klyric);
            songEntity.setPicture("");
            songEntity.setDuration("");
            songEntity.setCategory(0);
            songService.addSong(songEntity);
            log.info("new songs--------"+songEntity);
        }
    }
}
