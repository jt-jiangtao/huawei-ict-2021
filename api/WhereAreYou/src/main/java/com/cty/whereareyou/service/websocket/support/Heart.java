package com.cty.whereareyou.service.websocket.support;

import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/18 23:13
 */
@Data
public class Heart {
    public static final String type = ClientSendType.HEART;
    private String userId;
}
