package com.zkl.l_music.controller;

import com.zkl.l_music.Bo.UserBo;
import com.zkl.l_music.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity addUser(HttpServletRequest request, @RequestBody UserBo userBo) {
        boolean res = userService.addUser(userBo,request);
        if(res) {
            return ResponseEntity.status(HttpStatus.CREATED).body("");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

}
