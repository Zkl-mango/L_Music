package com.zkl.l_music.controller;

import com.zkl.l_music.entity.RankEntity;
import com.zkl.l_music.entity.RankListEntity;
import com.zkl.l_music.service.RankService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.vo.RankDetailVo;
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
@RequestMapping(value = "/rankList")
public class RankController {

    @Resource
    RankService rankService;

    @PutMapping(value = "/{id}")
    public ResponseEntity rankList(HttpServletRequest request,@PathVariable String id) {
        boolean res = rankService.updateRank(id);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "")
    public ResponseEntity getRankAll(HttpServletRequest request) {
        Map<Object,Object> res = rankService.getRankList();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getRankDetail(HttpServletRequest request,@PathVariable String id) {
        Map<Object,Object> res = new HashMap<>();
        RankEntity rankEntity = rankService.getRankById(id);
        List<RankDetailVo> rankList = rankService.getRankListById(id);
        res.put("rankEntity",rankEntity);
        res.put("rankList",rankList);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }
}
