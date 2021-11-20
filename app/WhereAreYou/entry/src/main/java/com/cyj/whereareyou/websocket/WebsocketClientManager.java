package com.cyj.whereareyou.websocket;

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

    public static String address = "ws://8.136.37.208:8080/api/websocket/12";

    public static void reconnectClient() {
        if (client != null){
            client.close();
        }
        client = null;
        try {
            client = new MessageWebsocketClient(new URI(address), new Draft_6455());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        client.connect();
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
