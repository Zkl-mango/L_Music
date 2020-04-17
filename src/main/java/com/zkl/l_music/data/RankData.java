package com.zkl.l_music.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.dao.AlbumDao;
import com.zkl.l_music.dao.SingerDao;
import com.zkl.l_music.dao.SongDao;
import com.zkl.l_music.entity.*;
import com.zkl.l_music.service.AlbumService;
import com.zkl.l_music.service.RankService;
import com.zkl.l_music.service.SingerService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.UUIDGenerator;
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
@Component("RankData")
public class RankData {

    @Resource
    RankService rankService;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    SongService songService;
    @Resource
    SongDao songDao;
    @Resource
    AlbumDao albumDao;
    @Resource
    SingerDao singerDao;
    @Resource
    SingerService singerService;
    @Resource
    AlbumService albumService;
    @Resource
    SongData songData;

    public void getRankData() {
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.getForEntity
                (DataUrlConstant.url+"/toplist",String.class);
        JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
        JSONArray jsonArray = jsonobj.getJSONArray("list");
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String picture = jsonObject.getString("coverImgUrl");
            RankEntity rankEntity = new RankEntity();
            rankEntity.setId(id);
            rankEntity.setRankName(name);
            rankEntity.setPlayNum(0);
            rankEntity.setPicture(picture);
            rankEntity.setRecomment(0);
            System.out.println(rankEntity);
            rankService.addRank(rankEntity);
        }
    }


    public void getRankDataDetails(String id) throws ParseException {
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.getForEntity
                (DataUrlConstant.url+"/top/list?idx={id}",String.class,params);
        JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
        JSONObject json1 = jsonobj.getJSONObject("playlist");
        JSONArray jsonArray = json1.getJSONArray("tracks");
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String songId = jsonObject.getString("id");
            SongEntity songEntity = judgeSong(songId,jsonObject);


            RankListEntity rankListEntity = new RankListEntity();
            int recomment = 0;
            if(i<3) {
                recomment = 1;
            }
            rankListEntity.setId(uuidGenerator.generateUUID());
            rankListEntity.setRankId(rankService.getRankById(id));
            rankListEntity.setSongId(songEntity);
            rankListEntity.setRecomment(recomment);
            rankService.addRankList(rankListEntity);
        }
    }

    public SongEntity judgeSong(String songId,JSONObject jsonObject) throws ParseException {
        JSONObject alObject = jsonObject.getJSONObject("al");
        String albumId = alObject.getString("id");
        JSONArray singerArray = jsonObject.getJSONArray("ar");
        //判断歌曲是否存在
        SongEntity songEntity = songDao.selectById(songId);
        if(songEntity == null) {
            //歌曲不存在判断专辑是否存在
            AlbumEntity albumEntity = albumDao.selectById(albumId);
            if(albumEntity == null) {
                //专辑不存在判断歌手是否存在
                for(int j=1;j<singerArray.size();j++) {
                    String singerId = singerArray.getJSONObject(j).getString("id");
                    SingerEntity singerEntity = singerDao.selectById(singerId);
                    if(singerEntity == null) {
                        singerEntity = newSinger(singerId);
                        if(singerEntity == null) {
                            continue;
                        }
                    }
                    newAlbum(albumId,singerEntity);
                }
            }
            try{
                songData.getSongData(albumId);
            } catch (Exception e) {
                log.info(e.toString());
                System.out.println("error"+albumId);
            }
            songEntity = songDao.selectById(songId);
            if(songEntity == null) {
                songEntity = newSong(songId,albumId);
            }
        }
        return songEntity;
    }


    public SingerEntity newSinger(String id) {
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity;
        try {
            responseEntity=restTemplate.getForEntity
                    (DataUrlConstant.url+"/artists?id={id}",String.class,params);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
        JSONObject jsonObject = jsonobj.getJSONObject("artist");
        String name = jsonObject.getString("name");
        JSONArray aliasArray = jsonObject.getJSONArray("alias");
        String englishName = "";
        if(aliasArray.size()!=0) {
            englishName = aliasArray.getString(0);
            for(int j=1;j<aliasArray.size();j++) {
                englishName += ","+aliasArray.getString(j);
            }
        }
        String picture = jsonObject.getString("img1v1Url");
        int songs = jsonObject.getInteger("musicSize");
        int albums = jsonObject.getInteger("albumSize");
        SingerEntity singerEntity = new SingerEntity();
        singerEntity.setId(id);
        singerEntity.setSinger(name);
        singerEntity.setEnglishName(englishName);
        singerEntity.setPicture(picture);
        singerEntity.setSongs(songs);
        singerEntity.setAlbums(albums);
        singerEntity.setFans(0);
        String sex="";
        singerEntity.setSex(sex);
        singerEntity.setCategory(Integer.valueOf("5000"));
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
        return singerEntity;
    }


    public void newAlbum(String id,SingerEntity singer) throws ParseException {
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.getForEntity
                (DataUrlConstant.url+"/album?id={id}",String.class,params);
        JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
        JSONObject jsonObject = jsonobj.getJSONObject("album");
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
            log.info("new albums------"+albumEntity);
        }
    }

    public SongEntity newSong(String id,String albumid) {
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.getForEntity
                (DataUrlConstant.url+"/song/detail?ids={id}",String.class,params);
        JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
        JSONArray jsonArray = jsonobj.getJSONArray("songs");
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            JSONObject alObject = jsonObject.getJSONObject("al");
            String picture = alObject.getString("picUrl");
            JSONArray singerArray = jsonObject.getJSONArray("ar");
            String singerId = singerArray.getJSONObject(0).getString("id");
            for (int j = 1; j < singerArray.size(); j++) {
                singerId += "," + singerArray.getJSONObject(j).getString("id");
            }
            //调用api获取歌曲连接
            String url = "";
            String lyric = "";
            String klyric = "";
            try{
                ResponseEntity<String> responseEntity_lyric=restTemplate.getForEntity
                        (DataUrlConstant.url+"/lyric?id={id}",String.class,params);
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
            songEntity.setCategory("");
            songService.addSong(songEntity);
            log.info("new songs--------"+songEntity);
            return songEntity;
        }
        return null;
    }
}
