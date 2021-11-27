package com.cyj.whereareyou.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyj.whereareyou.data.UserDataSource;
import com.cyj.whereareyou.websocket.push.NotificationManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class MessageWebsocketClient extends WebSocketClient {

    public MessageWebsocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
        Reconnect.resetEvent();
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LiveHeart.createHeart(this);
        System.out.println("握手成功");
    }

    @Override
    public void onMessage(String msg) {
        System.out.println("收到消息=========="+msg);
        //解析发送的报文
        JSONObject jsonObject = JSON.parseObject(msg);
        String type = jsonObject.getString("type");
        if (type != null && type.equals("HEART_SERVER_BACK")){
            Reconnect.resetEvent();
        }else if (type != null && type.equals("NOTIFICATION")){
            NotificationManager.push(jsonObject);
            send("{'userId': '" + UserDataSource.userId + "', 'type': 'CONFIRM_INFO', 'messageId': '" + jsonObject.getInteger("id") + "'}");
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("链接已关闭");
        LiveHeart.cancel();
    }

    @Override
    public void onError(Exception e){
        e.printStackTrace();
        System.out.println("发生错误已关闭");
    }
}
