package com.zkl.l_music.controller;

import com.zkl.l_music.entity.PlayListEntity;
import com.zkl.l_music.service.PlayListService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.PlayListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/play")
public class PlayListController {

    @Resource
    PlayListService playListService;

    //添加到播放列表
    @PostMapping(value = "")
    public ResponseEntity addPlayList(@RequestBody PlayListVo playListVo) {
        String res = playListService.addPlayList(playListVo);
        if(!StringUtils.isBlank(res)) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.fail("系统异常"));
    }

    //更新播放列表是否播放
    @PutMapping(value = "/{id}")
    public ResponseEntity updatePlayList(HttpServletRequest request,@PathVariable String id) {
        String  userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)|| userId.equals("undefined")) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
        }
        boolean res = playListService.updatePlayList(id,userId);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail("系统异常"));
    }

    //删除播放列表某一首歌
    @DeleteMapping(value = "/deleteOne/{id}")
    public ResponseEntity deleteOnePlayList(@PathVariable String id) {
        boolean res = playListService.deletePlayList(id);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.fail("系统异常"));
    }

    //清空播放列表
    @DeleteMapping(value = "/deleteAll")
    public ResponseEntity deletePlayList(HttpServletRequest request) {
        String  userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)|| userId.equals("undefined")) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
        }
        boolean res = playListService.deletePlayListByUser(userId);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
        }
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.fail("系统异常"));
    }
}
