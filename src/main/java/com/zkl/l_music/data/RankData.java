package com.zkl.l_music.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.SmallTagEntity;
import com.zkl.l_music.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@Component("RankData")
public class RankData {

    @Resource
    RankService rankService;

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

}
