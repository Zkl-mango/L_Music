package com.zkl.l_music.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.bo.UserBo;
import com.zkl.l_music.dao.SongDetailsDao;
import com.zkl.l_music.dao.SongListDao;
import com.zkl.l_music.dao.UserDao;
import com.zkl.l_music.entity.*;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.SecurityUtil;
import com.zkl.l_music.util.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sun.plugin.util.UIUtil;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("SongListData")
public class SongListData {

    @Resource
    UserDao userDao;
    @Resource
    SongListDao songListDao;
    @Resource
    UserService userService;
    @Resource
    UUIDGenerator uuidGenerator;
    @Resource
    SongDetailsDao songDetailsDao;
    @Resource
    RankData rankData;

    public void getSongListData(String cat) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("cat", cat);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity
                (DataUrlConstant.url + "/top/playlist?limit=350&cat={cat}&order=hot", String.class, params);
        JSONObject jsonobj = JSONObject.parseObject(responseEntity.getBody());
        JSONArray jsonArray = jsonobj.getJSONArray("playlists");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            SongListEntity songListEntity1 = songListDao.selectById(id);
            if(songListEntity1 !=null) {
                continue;
            }
            String listName = jsonObject.getString("name");
            String userId = jsonObject.getString("userId");
            String introduction = jsonObject.getString("description");
            String picture = jsonObject.getString("coverImgUrl");
            String time = jsonObject.getString("createTime");
            if(time.length()>13) {
                time = time.substring(0,13);
            }
            double timed = Double.parseDouble(time);
            SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            String formatDate = format.format(timed);
            Date date = format.parse(formatDate);
            JSONArray tags = jsonObject.getJSONArray("tags");
            String tag = tags.getString(0);
            for(int j=1;j<tags.size();j++) {
                tag = tag+","+tags.getString(j);
            }
            UserEntity userEntity = userDao.selectById(userId);
            if(userEntity == null) {
                JSONObject creator = jsonObject.getJSONObject("creator");
                String userName = creator.getString("nickname");
                userEntity = new UserEntity();
                userEntity.setName(userName);
                userEntity.setPassword(SecurityUtil.encryptPassword("123456"));
                userEntity.setPhone("13000000000");
                userEntity.setAvatar(ConstantUtil.avatar);
                userEntity.setId(userId);
                userDao.insert(userEntity);
                //生成默认 我喜欢 歌单
                SongListEntity songListEntity = new SongListEntity();
                songListEntity.setId( uuidGenerator.generateUUID());
                songListEntity.setCategory(2);
                songListEntity.setListName("我喜欢");
                songListEntity.setUserId(userEntity);
                songListDao.insert(songListEntity);
            }
            SongListEntity songListEntity = new SongListEntity();
            songListEntity.setId(id);
            songListEntity.setLikeNum(0);
            songListEntity.setPlayNum(0);
            songListEntity.setPicture(picture);
            songListEntity.setListName(listName);
            songListEntity.setCategory(1);
            songListEntity.setCreatTime(date);
            songListEntity.setIntroduction(introduction);
            songListEntity.setTag(tag);
            songListEntity.setUserId(userEntity);
            songListDao.insert(songListEntity);
            System.out.println("songList---"+cat+" to "+i+"id"+id);
            getSongListDetailData(id);
        }
    }


    public void getSongListDetailData(String songListId) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> params = new HashMap<>();
        params.put("id", songListId);
        SongListEntity songListEntity = songListDao.selectById(songListId);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity
                (DataUrlConstant.url + "/playlist/detail?id={id}", String.class, params);
        JSONObject jsonobj = JSONObject.parseObject(responseEntity.getBody());
        JSONObject jsonObject = jsonobj.getJSONObject("playlist");
        JSONArray jsonArray = jsonObject.getJSONArray("tracks");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String songId = json.getString("id");

            SongEntity songEntity = rankData.judgeSong(songId,json);

            SongDetailsEntity songDetailsEntity = new SongDetailsEntity();
            songDetailsEntity.setDeleted(0);
            songDetailsEntity.setSongList(songListEntity);
            songDetailsEntity.setId(uuidGenerator.generateUUID());
            songDetailsEntity.setCreateAt(new Date());
            songDetailsEntity.setSongId(songEntity);
            songDetailsDao.insert(songDetailsEntity);
        }
    }
}
