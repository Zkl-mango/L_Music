package com.zkl.l_music.controller;

import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.service.AlbumService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongListVo;
import com.zkl.l_music.vo.SongVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "")
public class IndexController {

    @Resource
    SongService songService;
    @Resource
    AlbumService albumService;
    @Resource
    SongListService songListService;

    @GetMapping("/index")
    public ResponseEntity getIndex() {
        List<SongListDetailVo> hot = songService.getSongsByHot();
        List<AlbumEntity> recommend = albumService.getNewAlbum();
        List<SongListVo> songList = songListService.getHotSongList();
        Map<String,Object> res = new HashMap<>();
        res.put("hot",hot);
        res.put("recommend",recommend);
        res.put("songList",songList);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }

}
