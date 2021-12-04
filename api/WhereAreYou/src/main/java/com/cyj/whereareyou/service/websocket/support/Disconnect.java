package com.cyj.whereareyou.service.websocket.support;

import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/18 23:14
 */
@Data
public class Disconnect {
    public static final String type = ClientSendType.DISCONNECT;
    private String userId;
}
