package com.cyj.whereareyou.websocket.push;

import com.alibaba.fastjson.JSONObject;
import com.cyj.whereareyou.data.entity.Notification;
import com.cyj.whereareyou.page.MainAbility;
import com.cyj.whereareyou.service.NotificationBridge;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.data.rdb.ValuesBucket;
import ohos.event.notification.NotificationHelper;
import ohos.event.notification.NotificationRequest;
import ohos.hiviewdfx.HiLog;
import ohos.rpc.RemoteException;
import ohos.utils.net.Uri;

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


        DataAbilityHelper helper = DataAbilityHelper.creator(new NotificationBridge());
        try {
            ValuesBucket valuesBucket = new ValuesBucket();
            valuesBucket.putString(Notification.DB_COLUMN_TITLE, "title");
            valuesBucket.putString(Notification.DB_COLUMN_CONTENT, "content");
            valuesBucket.putString(Notification.DB_COLUMN_TIME, "time");
            valuesBucket.putString(Notification.DB_COLUMN_TYPE, "type");
            helper.insert(Uri.parse(Notification.BASE_URI + Notification.DATA_PATH),valuesBucket);

            NotificationHelper.publishNotification(request);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }
    }
}
