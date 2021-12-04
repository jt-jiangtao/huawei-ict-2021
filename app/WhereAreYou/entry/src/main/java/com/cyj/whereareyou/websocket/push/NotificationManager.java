package com.cyj.whereareyou.websocket.push;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyj.whereareyou.data.entity.Notification;
import com.cyj.whereareyou.page.MainAbility;
import com.cyj.whereareyou.service.NotificationBridge;
import com.cyj.whereareyou.service.NotificationService;
import com.cyj.whereareyou.service.OpenUIService;
import com.cyj.whereareyou.websocket.WebsocketClientManager;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.aafwk.content.Operation;
import ohos.event.intentagent.IntentAgent;
import ohos.event.intentagent.IntentAgentConstant;
import ohos.event.intentagent.IntentAgentHelper;
import ohos.event.intentagent.IntentAgentInfo;
import ohos.event.notification.NotificationHelper;
import ohos.event.notification.NotificationRequest;
import ohos.rpc.RemoteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationManager {
    public static void push(JSONObject msg){
        notificationClew(msg.getString("content"), msg);
    }

    public static void notificationClew(String m, JSONObject msg){

        JSONObject jsonObject = JSON.parseObject(m);
        NotificationRequest request = new NotificationRequest();
        request.setTapDismissed(true);
//        request.setIntentAgent();
        NotificationRequest.NotificationNormalContent content = new NotificationRequest.NotificationNormalContent();
        if (msg.getString("n_type").equals("CLEW")){
            content.setTitle("用户提供线索").setText(jsonObject.getString("content"));
        }else if (msg.getString("n_type").equals("FIND")){
            content.setTitle("对比成功,请确认").setText(jsonObject.getString("content"));
        }else {
            content.setTitle("系统通知").setText(jsonObject.getString("content"));
        }
        NotificationRequest.NotificationContent notificationContent = new NotificationRequest.NotificationContent(content);
        request.setContent(notificationContent);
        if ( WebsocketClientManager.context != null){
            request.setIntentAgent(getIntentAgent(msg.toJSONString()));
        }

        try {
            NotificationHelper.publishNotification(request);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static IntentAgent getIntentAgent(String content) {
        Operation operation = new Intent.OperationBuilder().withDeviceId("")
                .withBundleName("com.cyj.whereareyou")
                .withAbilityName(MainAbility.class.getName())
                .build();
        Intent intent = new Intent();
        intent.setParam("type","notification");
        intent.setParam("content", content);
        intent.setOperation(operation);
        IntentParams intentParams = new IntentParams();
        List<Intent> intents = new ArrayList<>();
        intents.add(intent);
        IntentAgentInfo agentInfo = new IntentAgentInfo(100, IntentAgentConstant.OperationType.START_ABILITY,
                IntentAgentConstant.Flags.UPDATE_PRESENT_FLAG, intents, intentParams);
        return IntentAgentHelper.getIntentAgent(WebsocketClientManager.context, agentInfo);
    }
}
