package com.cyj.whereareyou.websocket;

import com.cyj.whereareyou.data.UserDataSource;

import java.util.Timer;
import java.util.TimerTask;

public class LiveHeart {
    private static Timer timer;

    public static void createHeart(MessageWebsocketClient client) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                client.send("{'userId': '" + UserDataSource.userId + "', 'type': 'HEART'}");
                System.out.println("heart -->");
            }
        },0, 1000 * 3);
    }

    public static void cancel(){
        if (timer != null) timer.cancel();
    }
}
