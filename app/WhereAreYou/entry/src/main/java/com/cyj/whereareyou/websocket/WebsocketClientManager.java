package com.cyj.whereareyou.websocket;

import com.cyj.whereareyou.data.UserDataSource;
import com.cyj.whereareyou.service.NotificationService;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.Timer;
import java.util.TimerTask;

public class WebsocketClientManager {
    private static Boolean lockReconnect = false;

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
