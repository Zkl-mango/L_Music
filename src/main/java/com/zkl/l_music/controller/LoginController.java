package com.zkl.l_music.controller;

import com.zkl.l_music.bo.LoginBo;
import com.zkl.l_music.entity.UserEntity;
import com.zkl.l_music.service.AuthService;
import com.zkl.l_music.service.UserService;
import com.zkl.l_music.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class LoginController {

    @Resource
    UserService userService;
    @Resource
    AuthService authService;

    /**
     * 用于确认服务器状态的接口 check_network
     * @return Map 相应数据
     */
    @GetMapping(produces="application/json;charset=UTF-8",value="check_network")
    @ResponseBody
    public ResponseEntity checkNetwork(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("ok"));
    }

    /**
     * 用于校验身份信息Access Token合法性的接口(隐式登录) auth_comform
     * @param request 请求实体
     * @return Map 相应数据
     */
    @GetMapping(value="auth_comfirm")
    @ResponseBody
    public ResponseEntity comfirmAuth(HttpServletRequest request,String token,String userId){
        if(authService.veriflyToken(userId,token)){
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("ok"));
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.success("登录信息发生了变化，请重新登录"));
        }
    }

    /**
     * 登录（手机密码登录）
     * @param request
     * @param loginBo
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity login(HttpServletRequest request, @RequestBody @Valid LoginBo loginBo) {
        Map<String,Object> res = userService.judgeLogin(request,loginBo);
        if(res.get("message").equals("登录成功")) {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(res));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(res));
    }

    @GetMapping("/loginOut")
    public ResponseEntity loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success());
    }

}
