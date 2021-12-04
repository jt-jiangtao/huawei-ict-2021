package com.cty.whereareyou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cty.whereareyou.service.ClewService;
import com.cty.whereareyou.service.websocket.WebSocketServer;
import com.cty.whereareyou.utils.UsernameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

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
    public Object provideClew(int fromUser, String toUser, String clew){
        return clewService.provideClew(fromUser, UsernameUtils.transformToId(toUser), clew);
    }

    @PostMapping("/seen")
    public Object seenNotification(int id){
        return clewService.updateSeenStatus(id);
    }

    @GetMapping("/get")
    public Object selectUserNotification(String userId){
        return clewService.selectUserNotification(UsernameUtils.transformToId(userId));
    }

    @GetMapping("/unseen")
    public Object unseenMessage(String userId){
        return clewService.unseenMessage(UsernameUtils.transformToId(userId));
    }
}
