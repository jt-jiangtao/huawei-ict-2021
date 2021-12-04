package com.cyj.whereareyou.service.websocket.support;

import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/18 23:06
 */
@Data
public class ConfirmInfo {
    public static final String type = ClientSendType.CONFIRM_INFO;
    private int messageId;
    private boolean dealStatus;
    private String userId;
}
