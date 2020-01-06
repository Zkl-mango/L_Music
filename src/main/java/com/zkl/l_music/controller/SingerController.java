package com.zkl.l_music.controller;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.service.SingerService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/singer")
public class SingerController {

    @Resource
    SingerService singerService;


    /**
     * 关注/取消关注歌手
     * @param id
     * @param flag
     * @return
     */
    @PutMapping(value="/{id}")
    public ResponseEntity updateSinger(@PathVariable String id, int flag) {
        boolean res = singerService.updateSinger(id,flag);
        if(res) {
            if(flag == -1) {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("取消关注成功"));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("关注成功"));
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    @GetMapping(value="/singerList")
    public ResponseEntity getSingerList(@RequestBody @Valid PageBo pageBo) {
        return null;
    }
}
