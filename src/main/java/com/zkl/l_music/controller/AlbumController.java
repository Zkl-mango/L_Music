package com.zkl.l_music.controller;

import com.zkl.l_music.service.AlbumService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.AlbumDetailVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/album")
public class AlbumController {

    @Resource
    AlbumService albumService;

    /**
     * 收藏/取消收藏 专辑接口
     * @param id
     * @param flag
     * @return
     */
    @PutMapping(value="/{id}")
    public ResponseEntity updateSinger(@PathVariable String id, int flag) {
        boolean res = albumService.updateAlbum(id,flag);
        if(res) {
            if(flag == -1) {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("取消收藏成功"));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("收藏成功"));
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 获取歌手对应专辑
     * @param singerId
     * @return
     */
    @GetMapping(value = "/list/{singerId}")
    public ResponseEntity getAlbumsBySinger(@PathVariable String singerId) {
        List list = albumService.getAlbumsBySinger(singerId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(list));
    }

    /**
     * 获取新专辑
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseEntity getNewAlbums() {
        List list = albumService.getNewAlbums();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(list));
    }

    /**
     * 获取专辑信息详情
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity getAlbumById(@PathVariable String id) {
        AlbumDetailVo albumDetailVo = albumService.getAlbumById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(albumDetailVo));
    }
}
