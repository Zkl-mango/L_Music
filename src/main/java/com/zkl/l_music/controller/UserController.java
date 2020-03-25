package com.zkl.l_music.controller;

import com.zkl.l_music.bo.UserBo;
import com.zkl.l_music.bo.UserPwdBo;
import com.zkl.l_music.entity.AuthEntity;
import com.zkl.l_music.entity.CommentsEntity;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.AuthService;
import com.zkl.l_music.service.CommentsLikeService;
import com.zkl.l_music.service.CommentsService;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.HandleAvatarUtil;
import com.zkl.l_music.util.RequestHolder;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.CommentLikesVo;
import com.zkl.l_music.vo.CommentsVo;
import com.zkl.l_music.vo.MyCommentVo;
import com.zkl.l_music.vo.UserVo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    AuthService authService;
    @Resource
    CommentsService commentsService;
    @Resource
    CommentsLikeService commentsLikeService;

    /**
     * 注册新用户
     * @param userBo
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity addUser( @RequestBody @Valid UserBo userBo) throws Exception {
        if(StringUtils.isBlank(userBo.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("密码为空，请重新输入"));
        }
        UserEntity userEntity = userService.addUser(userBo);
        if(userEntity != null) {
            AuthEntity authEntity = authService.addToken(userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(authEntity));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail("注册失败"));
    }

    /**
     * 修改用户基本信息
     * @param userBo
     * @return
     */
    @PutMapping(value="/{id}")
    public ResponseEntity updateUser( @PathVariable String id,@RequestBody @Valid UserBo userBo) {
        if(StringUtils.isBlank(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        boolean res = userService.updateUser(userBo,id);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(id));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 更新头像
     * @param request
     * @param id
     * @return
     */
    @PostMapping(value="/{id}/uploadImage")
    public ResponseEntity uploadImage(HttpServletRequest request, @PathVariable String id,@RequestParam("file") MultipartFile file) {
        if(StringUtils.isBlank(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        boolean res = userService.updateUserAvatar(file,id);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(id));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 注销用户信息
     * @param request
     * @return
     */
    @DeleteMapping(value="/{id}")
    public ResponseEntity deleteUser(HttpServletRequest request, @PathVariable String id) {
        if(StringUtils.isBlank(id)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        boolean res = userService.deleteUser(id);
        if(res) {
            request.getSession().invalidate();
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("注销成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 获取用户基本信息
     * @param request
     * @return
     */
    @GetMapping(value="/{id}")
    public ResponseEntity getUser(HttpServletRequest request, @PathVariable String id) {
        if(StringUtils.isBlank(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        UserVo userVo = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(userVo));
    }

    /**
     * 重置密码
     * @param userPwdBo
     * @return
     */
    @PutMapping(value="/reset_pwd")
    public ResponseEntity updatePassword(@RequestBody @Valid UserPwdBo userPwdBo) {
        boolean res = userService.updateUserPassword(userPwdBo);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("重置成功，请登录"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.fail("手机号和昵称不匹配"));
    }

    /**
     * 判断是否重名或者重手机号
     * @param request
     * @param name
     * @param type
     * @return
     */
    @GetMapping(value = "/judge_user/{name}/{type}")
    public ResponseEntity judgeUserByNameAndPhone(HttpServletRequest request,@PathVariable String name,@PathVariable int type) {
        String id = request.getHeader("userId");
        boolean res = userService.judgeUserNameAndPhone(name,type,id);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
        } else {
            if(type == 0) {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.fail("该手机号已经存在"));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.fail("该昵称已经存在"));
            }
        }
    }

    /**
     * 获取用户发表的评论
     * @param request
     * @return
     */
    @GetMapping(value = "/comments")
    public ResponseEntity getUserComment(HttpServletRequest request) {
        String id = request.getHeader("userId");
        List<MyCommentVo> commentsList = commentsService.getCommentsByUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(commentsList));
    }

    /**
     * 获取用户点赞的评论
     * @param request
     * @return
     */
    @GetMapping(value = "/likes")
    public ResponseEntity getUserLikeComment(HttpServletRequest request) {
        String id = request.getHeader("userId");
        List<CommentLikesVo> commentsList = commentsLikeService.getCommentLikeByUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(commentsList));
    }
}
