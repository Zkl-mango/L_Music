package com.zkl.l_music.controller;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.service.AlbumService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.AlbumDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
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
    @PutMapping(value="/{id}/{flag}")
    public ResponseEntity updateSinger(HttpServletRequest request, @PathVariable String id,@PathVariable int flag) {
        String userId = request.getHeader("userId");
        boolean res = albumService.updateAlbumByFlag(id,flag,userId);
        if(res) {
            if(flag == -1) {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("取消收藏成功"));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("已收藏到我的喜欢"));
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
    public ResponseEntity getAlbumsBySinger(@PathVariable String singerId,PageBo pageBo) {
        List list = albumService.getAlbumsBySinger(pageBo,singerId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(list));
    }

    /**
     * 获取新专辑
     * @return
     */
    @GetMapping(value = "/list")
    public ResponseEntity getNewAlbums(String id,String singerId) {
        List list = albumService.getNewAlbumsBySinger(id,singerId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(list));
    }

    /**
     * 获取专辑信息详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity getAlbumById(HttpServletRequest request,@PathVariable String id) {
        String userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            userId = null;
        } else {
            if(userId.equals("undefined")) {
                userId = null;
            }
        }
        AlbumDetailVo albumDetailVo = albumService.getAlbumById(id,userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(albumDetailVo));
    }
}
