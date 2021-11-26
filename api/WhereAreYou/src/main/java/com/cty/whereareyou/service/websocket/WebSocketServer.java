package com.cty.whereareyou.service.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cty.whereareyou.service.websocket.support.ClientSendType;
import com.cty.whereareyou.service.websocket.support.HeartConnectTimer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: jiangtao
 * @Date: 2021/11/18 21:32
 */
// TODO: Message push
@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocketServer {
    private static int onlineCount = 0;

    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    private Session session;

    private String userId="";

    private HeartConnectTimer heartConnectTimer;


    // TODO: 初始化时,将数据库未读消息读取到内存

    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {
        // TODO: 拉取内存中对应的消息
        this.session = session;
        this.userId=userId;
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
            //加入set中
        }else{
            webSocketMap.put(userId,this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("用户连接:"+userId+",当前在线人数为:" + getOnlineCount());
        this.heartConnectTimer = new HeartConnectTimer(this);

        try {
            Map<String, String> map = new HashMap<>();
            map.put("userId", userId);
            map.put("success", "connect succes");
            sendMessage(map.toString());
        } catch (IOException e) {
            log.error("用户:"+userId+",网络异常!!!!!!");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:"+userId+",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        // TODO: 收到消息确认通知,清除内存和数据库消息未读状态
        log.info("用户消息:"+userId+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId",this.userId);
                String toUserId=jsonObject.getString("userId");
                //传送给对应toUserId用户的websocket
                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
                    String type = jsonObject.getString("type");
                    jsonObject.put("type", jsonObject.get("type") + "_SERVER_BACK");
                    if (type == null)  jsonObject.put("error", "type is null");
                    else {
                        switch (type){
                            case ClientSendType.CONFIRM_INFO:
//                            {
//                                "userId": "1", "type": "CONFIRM_INFO", "messageId": "1265757"
//                            }
                                jsonObject = clientReceiveSuccess(jsonObject);
                                break;
                            case ClientSendType.DISCONNECT:
//                            {
//                                "userId": "1", "type": "DISCONNECT"
//                            }
                                heartConnectTimer.stop();
                                disconnectClient();
                                onClose();
                                return;
                            case ClientSendType.HEART:
//                            {
//                                "userId": "1", "type": "HEART"
//                            }
                                heartConnectTimer.resetTimer();
                                jsonObject.put("status","ok");
                                break;
                            default:
                                jsonObject.put("error", "type is error");
                                break;
                        }
                    }
                }else{
                    jsonObject.put("error", "userId not in server");
                    log.error("请求的userId:"+toUserId+"不在该服务器上");
                    // TODO: 保存未读消息(注意消息取舍)
                }
                webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private JSONObject clientReceiveSuccess(JSONObject jsonObject) {
        String messageId = jsonObject.getString("messageId");
        jsonObject.remove("messageId");
        // TODO: 修改数据库中信息和内存中消息状态
        jsonObject.put("status","ok");
        return jsonObject;
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送自定义消息
     * */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
        // TODO: 存储未读消息到数据库
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            log.error("用户"+userId+",不在线！");
            // TODO: 将消息存储到内存
        }
    }

    public boolean disconnectClient(){
        try {
            webSocketMap.get(userId).session.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
