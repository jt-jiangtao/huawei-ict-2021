package com.cyj.whereareyou.websocket;

import java.util.Timer;
import java.util.TimerTask;

public class Reconnect {
    private static Timer timer;

    public static void resetEvent(){
        cancelEvent();
        createEvent();
    }

    public static void createEvent(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("reconnect-->");
                WebsocketClientManager.reconnectClient();
            }
        }, 1000 * 30 , 1000 * 30);
    }

    public static void cancelEvent(){
        if (timer != null) timer.cancel();
    }
}
