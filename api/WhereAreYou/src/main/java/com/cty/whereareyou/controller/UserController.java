package com.cty.whereareyou.controller;

import com.cty.whereareyou.service.HuaweiInteractionService;
import com.cty.whereareyou.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 18:45
 */
@RestController
@CrossOrigin
@RequestMapping("/login")
@Slf4j
public class UserController {

    @Autowired
    private HuaweiInteractionService huaweiInteractionService;

    @Autowired
    private UserService loginService;

    @PostMapping("/log")
    public Object log(String code){
        log.info(code);
        return loginService.login(code);
    }

    @PostMapping("/verify")
    public Object verifyToken(String token){
        return loginService.verify(token);
    }

}