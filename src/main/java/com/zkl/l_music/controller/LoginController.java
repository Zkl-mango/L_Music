package com.zkl.l_music.controller;

import com.zkl.l_music.bo.LoginBo;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Resource
    UserService userService;

    @PutMapping("")
    public ResponseEntity login(HttpServletRequest request, @RequestBody @Valid LoginBo loginBo) {
        String message = userService.judgeLogin(loginBo);
        if(message.equals("登录成功")) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(message));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(message));
    }

}
