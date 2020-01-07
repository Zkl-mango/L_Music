package com.zkl.l_music.controller;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.service.SingerService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.PageInfoVo;
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

    /**
     * 获取歌手列表信息
     * @param pageBo
     * @return
     */
    @GetMapping(value="/singerList")
    public ResponseEntity getSingerList(@RequestBody @Valid PageBo pageBo) {
        PageInfoVo pageInfoVo = singerService.getSingers(pageBo);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(pageInfoVo));
    }

    /**
     * 根据性别获取歌手列表信息
     * @param sex
     * @param pageBo
     * @return
     */
    @GetMapping(value="/singerList/{sex}")
    public ResponseEntity getSingerListBySex(@PathVariable String sex,@RequestBody @Valid PageBo pageBo) {
        PageInfoVo pageInfoVo = singerService.getSingersBySex(pageBo, sex);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(pageInfoVo));
    }


}
