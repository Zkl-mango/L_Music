package com.zkl.l_music.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.entity.SingerEntity;
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
    @Resource
    SongDao songDao;

    //专辑获取歌曲信息
    public void getSongData(String albumid) {
        RestTemplate restTemplate=new RestTemplate();
        Map<String,Object> params=new HashMap<>();
        params.put("id",albumid);
        System.out.println(albumid);
        if(albumid.equals("86524786") || albumid.equals("3265406") || albumid.equals("86993099")
        || albumid.equals("87054787")) {
            return ;
        }
        ResponseEntity<String> responseEntity=restTemplate.getForEntity
                (DataUrlConstant.url+"/album?id={id}",String.class,params);
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
            int code = jsonobj.getInteger("code");
            JSONArray jsonArray = jsonobj.getJSONArray("songs");
            for(int i=0;i<jsonArray.size();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                SongEntity song = songDao.selectById(id);
                if(song !=null) {
                    continue;
                }
                String name = jsonObject.getString("name");
                JSONObject alObject = jsonObject.getJSONObject("al");
                String picture = alObject.getString("picUrl");
                JSONArray singerArray = jsonObject.getJSONArray("ar");
                String singerId = singerArray.getJSONObject(0).getString("id");
                for(int j=1;j<singerArray.size();j++) {
                    singerId +=","+ singerArray.getJSONObject(j).getString("id");
                }
                params.put("song_id",id);
                //调用api获取歌曲连接
                String url = "";
                try{
                    ResponseEntity<String> responseEntity_url=restTemplate.getForEntity
                            (DataUrlConstant.url+"/song/url?id={song_id}",String.class,params);

                    if(responseEntity_url.getStatusCode().is2xxSuccessful()) {
                        JSONObject json_url=JSONObject.parseObject(responseEntity_url.getBody());
                        JSONArray urlArray = json_url.getJSONArray("data");
                        url = urlArray.getJSONObject(0).getString("url");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("errorsongUrl");
                }
                String lyric = "";
                String klyric = "";
                try{
                    ResponseEntity<String> responseEntity_lyric=restTemplate.getForEntity
                            (DataUrlConstant.url+"/lyric?id={song_id}",String.class,params);
                    JSONObject json_lyric=JSONObject.parseObject(responseEntity_lyric.getBody());
                    JSONObject lyricArray1 = json_lyric.getJSONObject("lrc");

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
                } catch (Exception e) {
                    lyric = "找不到歌词信息";
                    klyric="";
                    System.out.println(e);
                    System.out.println("errorlyric");
                }
                //调用api获取歌词
                SongEntity songEntity = new SongEntity();
                songEntity.setId(id);
                songEntity.setRecommend(0);
                songEntity.setHot(0);
                songEntity.setLikeNum(0);
                songEntity.setAlbumId(albumid);
                songEntity.setName(name);
                songEntity.setSingerId(singerId);
                songEntity.setLink(url);
                songEntity.setLyric(lyric);
                songEntity.setKlyric(klyric);
                songEntity.setPicture(picture);
                songEntity.setCategory(0);
                songService.addSong(songEntity);
                log.info("new songs--------"+songEntity);
            }
        }

    }
}
