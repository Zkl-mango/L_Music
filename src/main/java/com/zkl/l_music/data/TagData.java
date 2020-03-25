package com.zkl.l_music.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.entity.SmallTagEntity;
import com.zkl.l_music.service.TagService;
import com.zkl.l_music.util.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component("TagData")
public class TagData {

    @Resource
    TagService tagService;
    @Resource
    UUIDGenerator uuidGenerator;

    public void getTagData() {
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity=restTemplate.getForEntity
                (DataUrlConstant.url+"/playlist/catlist",String.class);
        JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
        JSONArray jsonArray = jsonobj.getJSONArray("sub");
        System.out.println(jsonArray);
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            int category = jsonObject.getInteger("category");
            int hot = jsonObject.getInteger("hot");
            SmallTagEntity smallTagEntity = new SmallTagEntity();
            smallTagEntity.setCategory(category);
            smallTagEntity.setHot(hot);
            smallTagEntity.setName(name);
            smallTagEntity.setId(uuidGenerator.generateUUID());
            System.out.println(smallTagEntity);
            tagService.addSmallTag(smallTagEntity);
        }
    }
}
