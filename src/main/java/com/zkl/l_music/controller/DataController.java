package com.zkl.l_music.controller;

import com.zkl.l_music.data.AlbumData;
import com.zkl.l_music.data.SingerData;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.service.SingerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    @Resource
    SingerData singerData;
    @Resource
    SingerService singerService;
    @Resource
    AlbumData albumData;

    //添加歌手信息
    @RequestMapping("/singer/{cat}")
    public ResponseEntity<Void> getSingerFromApi(@PathVariable String cat) {
        singerData.getSingerData(cat);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //添加歌手信息
    @RequestMapping("/album/{cat}")
    public ResponseEntity<Void> getAlbumFromApi(@PathVariable int cat) {
        List<SingerEntity>singerList = singerService.getSingerByCategory(cat);
        for(int i=0;i<singerList.size();i++) {
            try {
                albumData.getAlbumData(singerList.get(i),1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}