package com.cty.whereareyou.service.websocket.support;

import com.cty.whereareyou.service.websocket.WebSocketServer;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author: jiangtao
 * @Date: 2021/11/19 10:25
 */
@Slf4j
public class HeartConnectTimer {
    // 定时器,心跳监测
    private Timer timer;

    private WebSocketServer webSocketServer;

    public HeartConnectTimer(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
        createTimer();
    }

    public boolean resetTimer(){
        timer.stop();
        createTimer();
        return true;
    }

    public boolean createTimer(){
        timer = new HashedWheelTimer();
        timer.newTimeout(timeout -> {
            log.info("connect auto break, remove it from list");
            webSocketServer.disconnectClient();
            webSocketServer.onClose();
        }, 10, TimeUnit.SECONDS);
        return true;
    }

    public boolean stop(){
        timer.stop();
        return true;
    }

    public Timer getTimer() {
        return timer;
    }
}
