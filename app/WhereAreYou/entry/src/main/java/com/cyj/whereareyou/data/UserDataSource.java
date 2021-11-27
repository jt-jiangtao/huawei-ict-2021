package com.cyj.whereareyou.data;

import com.cyj.whereareyou.service.NotificationService;
import com.cyj.whereareyou.websocket.WebsocketClientManager;

import java.util.UUID;

public class UserDataSource {
    public static String userId = "init@" + UUID.randomUUID();

    public static void setUserId(String userId) {
        UserDataSource.userId = userId;
        NotificationService.reconnectClient();
    }
}
