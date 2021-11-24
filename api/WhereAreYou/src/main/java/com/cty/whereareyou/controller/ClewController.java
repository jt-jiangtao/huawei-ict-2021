package com.cty.whereareyou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cty.whereareyou.service.ClewService;
import com.cty.whereareyou.service.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Object provideClew(int fromUser, int toUser, String clew){
        JSONObject jsonObject = JSON.parseObject("{}");
        jsonObject.put("fromUser", "test_user");
        jsonObject.put("toUser", "user@10000013");
        jsonObject.put("clew", "在公园见到了................");
        jsonObject.put("type", "NOTIFICATION");
        jsonObject.put("n_type", "CLEW");
        try {
            WebSocketServer.sendInfo(jsonObject.toJSONString(), "user@10000013");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clewService.provideClew(fromUser, toUser, clew);
    }
}
