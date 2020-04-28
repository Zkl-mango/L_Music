package com.zkl.l_music.controller;

import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.service.RecommentService;
import com.zkl.l_music.service.SongListService;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.vo.SongListDetailVo;
import com.zkl.l_music.vo.SongListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/recomment")
public class RecommentController {

    @Resource
    RecommentService recommentService;
    @Resource
    UserService userService;

    /**
     * 推荐
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity getAlbumsBySinger(HttpServletRequest request) {
        Map<String,Object> res = new HashMap<>();
        String userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            userId = "1";
            res.put("name","你好");
        } else {
            if(userId.equals("undefined")) {
                userId = "1";
                res.put("name","你好");
            } else {
                res.put("name",userService.getUserById(userId).getName());
            }
        }
        List<SongListDetailVo> list = recommentService.getRecommentsSong(userId);
        List<SongListVo> songListVos = recommentService.getRecommentsList(userId);
        res.put("songList",songListVos);
        res.put("list",list);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }

}
