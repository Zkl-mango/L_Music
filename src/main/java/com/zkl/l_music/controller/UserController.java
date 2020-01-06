package com.zkl.l_music.controller;

import com.zkl.l_music.bo.UserBo;
import com.zkl.l_music.bo.UserPwdBo;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.ApiResponse;
import com.zkl.l_music.util.ReturnCode;
import com.zkl.l_music.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 注册新用户
     * @param request
     * @param userBo
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity addUser(HttpServletRequest request, @RequestBody @Valid UserBo userBo) throws Exception {
        if(StringUtils.isBlank(userBo.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("密码为空，请重新输入"));
        }
        boolean res = userService.addUser(userBo,request);
        if(res) {
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail("注册失败"));
    }

    /**
     * 修改用户基本信息
     * @param request
     * @param userBo
     * @return
     */
    @PutMapping(value="")
    public ResponseEntity updateUser(HttpServletRequest request,@RequestBody @Valid UserBo userBo) {
        String id = (String)request.getSession().getAttribute("userId");
        if(StringUtils.isBlank(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        boolean res = userService.updateUser(userBo,id,request);
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
    @DeleteMapping(value="")
    public ResponseEntity deleteUser(HttpServletRequest request) {
        String id = (String)request.getSession().getAttribute("userId");
        if(StringUtils.isBlank(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(ReturnCode.NO_LOGIN));
        }
        boolean res = userService.deleteUser(id);
        if(res) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("注销成功"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

    /**
     * 获取用户基本信息
     * @param request
     * @return
     */
    @GetMapping(value="")
    public ResponseEntity getUser(HttpServletRequest request) {
        String id = (String)request.getSession().getAttribute("userId");
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
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("重置成功，请重新登录"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.fail(ReturnCode.FAIL));
    }

}
