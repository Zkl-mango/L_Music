package com.zkl.l_music.controller;

import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.HistoryListService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.HistoryListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
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
    @PostMapping("")
    public ResponseEntity addHistorys(HttpServletRequest request, @RequestBody String songId) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("userEntity");
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        boolean res = historyListService.addHistoryList(songId,user);
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
        String  userId = (String) request.getSession().getAttribute("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        List<HistoryListVo> list = historyListService.getHistoryListByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(list));
    }

}
