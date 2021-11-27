package com.cyj.whereareyou.service;

import com.cyj.whereareyou.data.UserDataSource;
import com.cyj.whereareyou.websocket.MessageWebsocketClient;
import com.cyj.whereareyou.websocket.WebsocketClientManager;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.event.notification.NotificationRequest;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import org.java_websocket.drafts.Draft_6455;

import java.net.URI;
import java.net.URISyntaxException;

public class NotificationService extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");

    @Override
    public void onStart(Intent intent) {
        HiLog.error(LABEL_LOG, "NotificationService::onStart");
        NotificationService.reconnectClient();
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


    public static MessageWebsocketClient client;

    public static String address = "ws://8.136.37.208:8080/api/websocket/";

    public static void reconnectClient() {
        if (client != null){
            client.close();
        }
        client = null;
        try {
            if (! UserDataSource.userId.startsWith("init@")){
                client = new MessageWebsocketClient(new URI(address + UserDataSource.userId), new Draft_6455());
                client.connect();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnect() {
        try {
            client.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            client = null;
        }
    }

    public static void send(byte[] bytes){
        client.send(bytes);
    }
}