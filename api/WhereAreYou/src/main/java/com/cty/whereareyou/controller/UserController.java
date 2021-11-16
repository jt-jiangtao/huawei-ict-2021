package com.cty.whereareyou.controller;

import com.cty.whereareyou.service.HuaweiInteractionService;
import com.cty.whereareyou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 18:45
 */
@RestController
@CrossOrigin
@RequestMapping("/login")
public class UserController {

    @Autowired
    private HuaweiInteractionService huaweiInteractionService;

    @Autowired
    private UserService loginService;

    @PostMapping("/log")
    public Object log(@RequestParam(name = "code") String code){
        return loginService.login(code);
    }

}