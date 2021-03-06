package com.zkl.l_music.controller;

import com.zkl.l_music.bo.CommentsBo;
import com.zkl.l_music.bo.PageBo;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.CommentsLikeService;
import com.zkl.l_music.service.CommentsService;
import com.zkl.l_music.service.SongService;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.RequestHolder;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.CommentsVo;
import com.zkl.l_music.vo.SongVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/comments")
public class CommentsController {

    @Resource
    UserService userService;
    @Resource
    CommentsService commentsService;
    @Resource
    CommentsLikeService commentsLikeService;
    @Resource
    SongService songService;

    /**
     * 添加评论
     * @param request
     * @param commentsBo
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity addComments(HttpServletRequest request, @RequestBody @Valid CommentsBo commentsBo) {
        String id = request.getHeader("userId");
        if(StringUtils.isBlank(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        boolean res = commentsService.addComments(commentsBo,id);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("添加评论成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 更新点赞(存入到redis)
     * @param type -1,取消点赞；1点赞
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}/{type}")
    public ResponseEntity updateComments(HttpServletRequest request,@PathVariable String id,@PathVariable int type) {
        String userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        if (type == 1) {
            //更新点赞数
            commentsLikeService.incrementLikedCount(id);
            //记录点赞数据
            commentsLikeService.saveLikedRedis(userId,id);
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("点赞成功"));
        }
        commentsLikeService.decrementLikedCount(id);
        commentsLikeService.unlikeFromRedis(userId,id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("取消点赞成功"));
    }

    /**
     * 删除评论
     * @param request
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletedComments(HttpServletRequest request, @PathVariable String id) {
        String userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        boolean res = commentsService.deleteComments(id,userId);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("删除评论成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 获取评论列表
     * @param songId
     * @param pageBo
     * @return
     */
    @GetMapping(value = "/{songId}")
    public ResponseEntity getComments(HttpServletRequest request,@PathVariable String songId, @Valid PageBo pageBo) {
        Map<String,Object> res = new HashMap<>();
        String userId = request.getHeader("userId");
        if(StringUtils.isBlank(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        CommentsVo commentsVo = commentsService.getCommentsBySong(pageBo,songId,userId);
        res.put("commentsVo",commentsVo);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
    }


}
