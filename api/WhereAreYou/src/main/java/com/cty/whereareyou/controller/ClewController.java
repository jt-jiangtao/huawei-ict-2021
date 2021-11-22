package com.cty.whereareyou.controller;

import com.cty.whereareyou.service.ClewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 20:26
 */
@RestController
@CrossOrigin
@RequestMapping("/clew")
public class ClewController {
    @Autowired
    private ClewService clewService;

    @PostMapping("/send")
    public Object provideClew(int fromUser, int toUser, String clew){
        // TODO: 实现推送
        return clewService.provideClew(fromUser, toUser, clew);
    }
}
