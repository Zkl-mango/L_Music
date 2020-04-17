package com.zkl.l_music.controller;

import com.zkl.l_music.data.*;
import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.SingerEntity;
import com.zkl.l_music.entity.SmallTagEntity;
import com.zkl.l_music.service.AlbumService;
import com.zkl.l_music.service.RankService;
import com.zkl.l_music.service.SingerService;
import com.zkl.l_music.service.TagService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DataController {

    @Resource
    SingerData singerData;
    @Resource
    SingerService singerService;
    @Resource
    AlbumData albumData;
    @Resource
    AlbumService albumService;
    @Resource
    SongData songData;
    @Resource
    TagData tagData;
    @Resource
    RankData rankData;
    @Resource
    RankService rankService;
    @Resource
    TagService tagService;
    @Resource
    SongListData songListData;

    //添加歌手信息
    @RequestMapping("/singer/{cat}")
    public ResponseEntity<Void> getSingerFromApi(@PathVariable String cat) {
        singerData.getSingerData(cat);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //添加专辑信息
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

    //添加歌曲信息
    @RequestMapping("/song/{cat}")
    public ResponseEntity getSongFromApi(@PathVariable int cat) {
        List<SingerEntity>singerList = singerService.getSingerByCategory(cat);
//        singerList = singerList.subList(1652,singerList.size());
        for(int j=0;j<400;j++) {
            List<AlbumEntity> list =  albumService.getAllAlbumsBySinger(singerList.get(j).getId());
            int length = 51;
            if(list.size() < 51) {
                length = list.size();
            }
            for(int i=0;i<length;i++) {
                try{
                    songData.getSongData(list.get(i).getId());
                } catch (Exception e) {
                    log.info(e.toString());
                    System.out.println("error"+j);
                }
                System.out.println(j+"  page"+i);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    //添加类别信息
    @RequestMapping("/tag")
    public ResponseEntity<Void> getTagFromApi() {
        tagData.getTagData();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping("/rank")
    public ResponseEntity getRankFromApi() {
        rankData.getRankData();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping("/rankDetail")
    public ResponseEntity getRankDetailFromApi() throws ParseException {
        List<RankEntity> ranks = rankService.getAllRank();
        for(int i=0;i<ranks.size();i++) {
            rankData.getRankDataDetails(ranks.get(i).getId());
            System.out.println("rank"+i);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping("/songList")
    public ResponseEntity getSongListFromApi() throws Exception {
        List<SmallTagEntity> tags = tagService.getSmallTagByCat(0);
        for(int i=2;i<tags.size();i++) {
            songListData.getSongListData(tags.get(i).getName());
            System.out.println("tags"+tags.get(i).getName());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
