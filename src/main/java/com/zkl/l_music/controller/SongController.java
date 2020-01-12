package com.zkl.l_music.controller;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SingerListVo;
import com.zkl.l_music.vo.SongVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/song")
public class SongController {

    @Resource
    SongService songService;

    /**
     * 更新歌曲热度
     * @param id
     * @param hot
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity updateSong(@PathVariable String id,int hot) {
        boolean res = songService.updateSong(id,hot,-1);
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
}
