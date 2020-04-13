package com.zkl.l_music.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.data.DataUrlConstant;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SingerListVo;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/song")
@Slf4j
public class SongController {

    @Resource
    SongService songService;

    /**
     * 更新歌曲热度/播放量
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity updateSong(@PathVariable String id) {
        boolean res = songService.updateSong(id,1,-1);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    @GetMapping("/{id}")
    public ResponseEntity getSongById(@PathVariable String id) {
        SongVo songVo = songService.getSongById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(songVo));
    }

    /**
     * 根据歌手获取歌曲
     * @param singerId
     * @param pageBo
     * @return
     */
    @GetMapping("/singer/{singerId}")
    public ResponseEntity getSongsBySinger(@PathVariable String singerId, @RequestBody @Valid PageBo pageBo) {
        PageInfoVo pageInfoVo = songService.getSongsBySinger(pageBo,singerId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(pageInfoVo));
    }

    /**
     * 根据专辑获取歌曲
     * @param albumId
     * @return
     */
    @GetMapping("/album/{albumId}")
    public ResponseEntity getSongsByAlbum(@PathVariable String albumId) {
        List<SongVo> list = songService.getSongsByAlbum(albumId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(list));
    }

    /**
     * 根据歌曲类别获取歌曲
     * @param type
     * @param pageBo
     * @return
     */
    @GetMapping("/category/{type}")
    public ResponseEntity getSongsByCategory(@PathVariable int type, @RequestBody @Valid PageBo pageBo) {
        PageInfoVo pageInfoVo = songService.getSongsByCategory(pageBo,type);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(pageInfoVo));
    }

    /**
     * 根据热度获取歌曲
     * @return
     */
    @GetMapping("/hot")
    public ResponseEntity getSongsByHot() {
        List<SongListDetailVo> list = songService.getSongsByHot();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(list));
    }

    /**
     * 查看歌曲是否可用并获取url
     * @param id
     * @return
     */
    @GetMapping("/check/{id}")
    public ResponseEntity songCheck(@PathVariable String id) {
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> responseEntity;
        Boolean type ;
        String message ;
        try {
            responseEntity=restTemplate.getForEntity
                    (DataUrlConstant.url+"/check/music?id="+id,String.class);
            JSONObject jsonobj=JSONObject.parseObject(responseEntity.getBody());
            type = jsonobj.getBoolean("success");
            message = jsonobj.getString("message");
        } catch (Exception e) {
            log.error(e.toString());
            type = false;
            message = "亲爱的,暂无版权";
        }
        System.out.println(DataUrlConstant.url+"/check/music?id="+id+" "+type+" "+message);
        if(type) {
            ResponseEntity<String> response_url=restTemplate.getForEntity
                    (DataUrlConstant.url+"/song/url?id="+id,String.class);
            JSONObject json_url=JSONObject.parseObject(response_url.getBody());
            JSONArray urlArray = json_url.getJSONArray("data");
            String url = urlArray.getJSONObject(0).getString("url");
            if(StringUtils.isBlank(url)) {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.fail("亲爱的,暂无版权"));
            }
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(url));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.fail(message));
    }
}
