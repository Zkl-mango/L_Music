package com.zkl.l_music.controller;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.service.SingerService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.PageInfoVo;
import com.zkl.l_music.vo.SingerDetailVo;
import com.zkl.l_music.vo.SingerListVo;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
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
    @PutMapping(value="/{id}/{flag}")
    public ResponseEntity updateSinger(HttpServletRequest request,@PathVariable String id,@PathVariable int flag) {
        String userId = request.getHeader("userId");
        boolean res = singerService.updateSinger(id,flag,userId);
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
     * @return
     */
    @GetMapping(value="/singerList/{category}/{sex}")
    public ResponseEntity getSingerList(HttpServletRequest request, @PathVariable int category, @PathVariable String sex,
                                        PageBo pageBo) {
        String userId = request.getHeader("userId");
        if(userId.equals("undefined")) {
            userId = null;
        }
        List<SingerListVo> singerListVos = singerService.getSingers(pageBo,sex,category,userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(singerListVos));
    }


    /**
     * 获取歌手详情
     * @param id
     * @param pageBo
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity getSingerById(@PathVariable String id, PageBo pageBo,int type) {
        SingerDetailVo singerDetailVo = singerService.getSingerById(id,pageBo,type);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(singerDetailVo));
    }

}
