package com.cyj.whereareyou.websocket.push;

import com.alibaba.fastjson.JSONObject;
import com.cyj.whereareyou.data.entity.Notification;
import com.cyj.whereareyou.page.MainAbility;
import com.cyj.whereareyou.service.NotificationBridge;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.event.notification.NotificationHelper;
import ohos.event.notification.NotificationRequest;
import ohos.rpc.RemoteException;

import java.util.Map;

public class NotificationManager {
    public static void push(JSONObject msg){
        notificationClew(msg.getString("content"), msg);
    }

    public static void notificationClew(String m, JSONObject msg){
        NotificationRequest request = new NotificationRequest();
        NotificationRequest.NotificationNormalContent content = new NotificationRequest.NotificationNormalContent();
        if (msg.getString("n_type").equals("CLEW")){
            content.setTitle("用户提供线索").setText(m);
        }else if (msg.getString("n_type").equals("FIND")){
            content.setTitle("对比成功,请确认").setText(m);
        }else {
            content.setTitle("系统通知").setText(m);
        }
        NotificationRequest.NotificationContent notificationContent = new NotificationRequest.NotificationContent(content);
        request.setContent(notificationContent);

        try {
            NotificationHelper.publishNotification(request);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
