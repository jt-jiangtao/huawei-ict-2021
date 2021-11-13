package com.cyj.whereareyou.service;

import com.cyj.whereareyou.service.support.RequestCode;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.rpc.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.zson.ZSONObject;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OpenUIService extends Ability {
    // 定义日志标签
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0, "MY_TAG");

    private MyRemote remote = new MyRemote();

    private static MessageParcel reply;

    private Set<IRemoteObject> remoteObjectHandlers = new HashSet<IRemoteObject>();

    // FA在请求PA服务时会调用Ability.connectAbility连接PA，连接成功后，需要在onConnect返回一个remote对象，供FA向PA发送消息
    @Override
    protected IRemoteObject onConnect(Intent intent) {
        super.onConnect(intent);
        return remote.asObject();
    }

    class MyRemote extends RemoteObject implements IRemoteBroker {
        private static final int SUCCESS = 0;
        private static final int ERROR = 1;
        public MessageParcel reply;

        MyRemote() {
            super("MyService_MyRemote");
        }

        @Override
        public boolean onRemoteRequest(int code, MessageParcel data, MessageParcel reply, MessageOption option) {
            OpenUIService.reply = reply;
            switch (code) {
                case RequestCode.TAKE_PHOTO: {
                    remoteObjectHandlers.add(data.readRemoteObject());
                    HiLog.info(LABEL, "OPENING");
                    Intent intent = new Intent();
                    Operation operation = new Intent.OperationBuilder()
                            .withDeviceId("")
                            .withBundleName("com.cyj.whereareyou")
                            .withAbilityName("com.cyj.whereareyou.page.TakePhotoAbility")
                            .build();
                    intent.setOperation(operation);
                    startAbility(intent);
                    break;
                }
                case RequestCode.SELECT_IMAGE:{
                    remoteObjectHandlers.add(data.readRemoteObject());
                    HiLog.info(LABEL, "OPENING");
                    Intent intent = new Intent();
                    Operation operation = new Intent.OperationBuilder()
//                            .withAction("android.media.action.IMAGE_CAPTURE")
                            .withDeviceId("")
                            .withBundleName("com.cyj.whereareyou")
                            .withAbilityName("com.cyj.whereareyou.page.SelectImageAbility")
                            .build();
                    intent.setOperation(operation);
                    startAbility(intent);
                    break;
                }

            }
            return true;
        }

        @Override
        public IRemoteObject asObject() {
            return this;
        }
    }

    @Override
    protected void onCommand(Intent intent, boolean restart, int startId) {
        switch (intent.getIntParam("code", -1)){
            case RequestCode.TAKE_PHOTO:
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("code", 1);
                try {
                    returnImage(intent.getStringParam("img"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }finally {
                    remoteObjectHandlers.clear();
                }
                break;
        }
        super.onCommand(intent, restart, startId);
    }


    private void returnImage(String d) throws RemoteException {
        MessageParcel data = MessageParcel.obtain();
        MessageParcel reply = MessageParcel.obtain();
        MessageOption option = new MessageOption();
        Map<String, Object> event = new HashMap<String, Object>();
        event.put("abilityEvent", "file://" + d);
        data.writeString(ZSONObject.toZSONString(event));
        // 如果仅支持单FA订阅，可直接触发回调：remoteObjectHandler.sendRequest(100, data, reply, option);
        for (IRemoteObject item : remoteObjectHandlers) {
            item.sendRequest(100, data, reply, option);
        }
        reply.reclaim();
        data.reclaim();
    }
}