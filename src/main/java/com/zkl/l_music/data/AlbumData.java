package com.zkl.l_music.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("AlbumData")
public class AlbumData {

    @Resource
    AlbumService albumService;

    public void getAlbumData(SingerEntity singer, int page) throws ParseException {
        RestTemplate restTemplate=new RestTemplate();
        Map<String,Object> params=new HashMap<>();
        params.put("id",singer.getId());
        params.put("offset",(page-1)*1000);
        ResponseEntity<String> responseEntity=restTemplate.getForEntity
                (DataUrlConstant.url+"/artist/album?id={id}&limit=1000&offset={offset}",String.class,params);
        JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
        JSONArray jsonArray = jsonobj.getJSONArray("hotAlbums");
        boolean more = jsonobj.getBoolean("more");
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            if(StringUtils.isBlank(name)) {
                name = "未知专辑";
            }
            String type = jsonObject.getString("type");
            int songs = jsonObject.getInteger("size");
            String picture = jsonObject.getString("blurPicUrl");
            String time = jsonObject.getString("publishTime");
            if(time.length()>13) {
                time = time.substring(0,13);
            }
            double timed = Double.parseDouble(time);
            SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            String formatDate = format.format(timed);
            Date date = format.parse(formatDate);

            AlbumEntity album = albumService.getAlbumEntityById(id);
            if(album!=null) {
                if(!album.getSingerList().contains(singer.getId())) {
                    album.setSingerId(null);
                    album.setSingerList(album.getSingerList()+","+singer.getId());
                    albumService.updateAlbum(album);
                }
            } else {
                AlbumEntity albumEntity = new AlbumEntity();
                albumEntity.setId(id);
                albumEntity.setName(name);
                albumEntity.setPicture(picture);
                albumEntity.setType(type);
                albumEntity.setSingerId(singer);
                albumEntity.setSongs(songs);
                albumEntity.setTime(date);
                albumEntity.setHot(0);
                albumEntity.setSingerList(singer.getId());
                albumService.addAlbum(albumEntity);
                log.info(page+"new albums------"+albumEntity);
            }

        }
        if(more) {
            page++;
            getAlbumData(singer,page);
        }
    }

}
