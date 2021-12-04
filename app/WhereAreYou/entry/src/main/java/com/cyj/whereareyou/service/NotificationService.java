package com.cyj.whereareyou.service;

import com.cyj.whereareyou.websocket.WebsocketClientManager;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.event.notification.NotificationRequest;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class NotificationService extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");

    @Override
    public void onStart(Intent intent) {
        HiLog.error(LABEL_LOG, "NotificationService::onStart");
        // 创建通知，其中1005为notificationId
//        NotificationRequest request = new NotificationRequest(1005);
//        NotificationRequest.NotificationNormalContent content = new NotificationRequest.NotificationNormalContent();
//        content.setTitle("title").setText("text");
//        NotificationRequest.NotificationContent notificationContent = new NotificationRequest.NotificationContent(content);
//        request.setContent(notificationContent);

        // 绑定通知，1005为创建通知时传入的notificationId
//        keepBackgroundRunning(1005, request);
        WebsocketClientManager.reconnectClient();
        super.onStart(intent);
    }

    @Override
    public void onBackground() {
        super.onBackground();
        HiLog.info(LABEL_LOG, "NotificationService::onBackground");
    }

    @Override
    public void onStop() {
        cancelBackgroundRunning();
        super.onStop();
        HiLog.info(LABEL_LOG, "NotificationService::onStop");
    }

    @Override
    public void onCommand(Intent intent, boolean restart, int startId) {

    }

    @Override
    public IRemoteObject onConnect(Intent intent) {
        return null;
    }

    @Override
    public void onDisconnect(Intent intent) {
    }


}