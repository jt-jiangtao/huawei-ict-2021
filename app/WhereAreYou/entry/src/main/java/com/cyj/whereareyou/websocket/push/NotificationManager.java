package com.cyj.whereareyou.websocket.push;

import com.alibaba.fastjson.JSONObject;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.event.notification.NotificationHelper;
import ohos.event.notification.NotificationRequest;
import ohos.rpc.RemoteException;

import java.util.Map;

public class NotificationManager {
    public static void push(JSONObject msg){
        if (msg.get("n_type").equals("CLEW")){
            notificationClew(msg.getString("clew"));
        }
    }

    public static void notificationClew(String clew){
        NotificationRequest request = new NotificationRequest();
        NotificationRequest.NotificationNormalContent content = new NotificationRequest.NotificationNormalContent();
        content.setTitle("用户提供线索").setText(clew);
        NotificationRequest.NotificationContent notificationContent = new NotificationRequest.NotificationContent(content);
        request.setContent(notificationContent);
        try {
            NotificationHelper.publishNotification(request);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
