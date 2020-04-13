package com.zkl.l_music.controller;

import com.zkl.l_music.entity.AlbumEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.HistoryListService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ConstantUtil;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.AlbumVo;
import com.zkl.l_music.vo.HistoryListVo;
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
@RequestMapping(value = "/history")
public class HistoryListController {

    @Resource
    HistoryListService historyListService;

    /**
     * 添加到历史播放
     * @param request
     * @param songId
     * @return
     */
    @PostMapping("/{type}/{songId}")
    public ResponseEntity addHistorys(HttpServletRequest request, @PathVariable String songId,
                                      @PathVariable int type) {
        String  userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        boolean res = historyListService.addHistoryList(songId,userId,type);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(ReturnCode.SUCCESS));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 获取历史列表
     * @param request
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity getHistorys(HttpServletRequest request) {
        String  userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        //喜欢的歌曲
        List<HistoryListVo> historyListVos = historyListService.getHistoryListByUser(userId);
        //喜欢的专辑
        List<AlbumVo> albumList = historyListService.getHistoryAlbumByUser(userId, ConstantUtil.albumType);
        //喜欢的歌单
        List<SongListVo> songlist = historyListService.getHistorySongByUser(userId,ConstantUtil.listType);
        Map<Object,Object> res = new HashMap<>();
        res.put("likeSong",historyListVos);
        res.put("albumList",albumList);
        res.put("songList",songlist);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }


    @DeleteMapping(value = "/{type}")
    public ResponseEntity DeleteHistorys(HttpServletRequest request,@PathVariable int type) {
        String  userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        historyListService.deleteHistoryLists(userId,type);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
    }
}
