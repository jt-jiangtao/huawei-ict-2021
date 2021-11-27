package com.cyj.whereareyou.service;

import com.alibaba.fastjson.JSON;
import com.cyj.whereareyou.data.UserDataSource;
import com.cyj.whereareyou.data.entity.Notification;
import com.cyj.whereareyou.data.entity.NotificationEntity;
import com.cyj.whereareyou.service.support.RequestCode;
import com.cyj.whereareyou.service.support.RequestParam;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.ability.IDataAbilityObserver;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.rdb.ValuesBucket;
import ohos.data.resultset.ResultSet;
import ohos.rpc.*;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;
import ohos.utils.zson.ZSONObject;

import java.util.*;

public class NotificationBridge extends Ability {
    private static NotificationBridge bridge;

    // 定义日志标签
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0, "MY_TAG");

    private MyRemote remote = new MyRemote();

    private static MessageParcel reply;

    private Set<IRemoteObject> remoteObjectHandlers = new HashSet<IRemoteObject>();

    private DataAbilityHelper databaseHelper;

    private final IDataAbilityObserver dataAbilityObserver = () -> {
        HiLog.info(LABEL, "%{public}s", "database change");
    };

    @Override
    protected IRemoteObject onConnect(Intent intent) {
        super.onConnect(intent);
        bridge = this;
        return remote.asObject();
    }

    private void initDatabaseHelper() {
        databaseHelper = DataAbilityHelper.creator(this);
        databaseHelper.registerObserver(Uri.parse(Notification.BASE_URI), dataAbilityObserver);
    }

    private void insert(String title, String content, String time, String type) {
        ValuesBucket valuesBucket = new ValuesBucket();
        valuesBucket.putString(Notification.DB_COLUMN_TITLE, title);
        valuesBucket.putString(Notification.DB_COLUMN_CONTENT, content);
        valuesBucket.putString(Notification.DB_COLUMN_TIME, time);
        valuesBucket.putString(Notification.DB_COLUMN_TYPE, type);
        try {
            databaseHelper.insert(Uri.parse(Notification.BASE_URI + Notification.DATA_PATH), valuesBucket);
        } catch (DataAbilityRemoteException | IllegalStateException exception) {
            HiLog.error(LABEL, "%{public}s", "insert: dataRemote exception|illegalStateException");
        }
    }

    private ResultSet queryAll(){
        String[] columns = new String[]{Notification.DB_COLUMN_ID, Notification.DB_COLUMN_TITLE, Notification.DB_COLUMN_CONTENT, Notification.DB_COLUMN_TYPE, Notification.DB_COLUMN_TIME, Notification.DB_COLUMN_READ_STATUS};
        DataAbilityPredicates predicates = new DataAbilityPredicates();
        ResultSet resultSet = null;
        try {
            resultSet = databaseHelper.query(Uri.parse(Notification.BASE_URI + Notification.DATA_PATH), columns, predicates);
        } catch (DataAbilityRemoteException | IllegalStateException exception) {
            HiLog.error(LABEL, "%{public}s", "query: dataRemote exception|illegalStateException");
        }
        return resultSet;
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
//            this.reply = reply;
            switch (code) {
                case 1001: {
                    initDatabaseHelper();
                    insert("title1", "content1", "time1", "type1");
                    insert("title2", "content2", "time2", "type2");
                    ResultSet resultSet = queryAll();
                    List<NotificationEntity> notificationEntities = new ArrayList<>();
                    if (resultSet.goToFirstRow()) {
                        do {
                            int id = resultSet.getInt(0);
                            String title = resultSet.getString(1);
                            String content = resultSet.getString(2);
                            String type = resultSet.getString(3);
                            String time = resultSet.getString(4);
                            int status = resultSet.getInt(5);
                            notificationEntities.add(new NotificationEntity(id, title, content, type, time, status));
                        } while (resultSet.goToNextRow());
                    }
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("code", SUCCESS);
                    result.put("abilityResult", JSON.toJSONString(notificationEntities));
                    reply.writeString(ZSONObject.toZSONString(result));
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

    }

    public static NotificationBridge getInstance(){
        return bridge;
    }
}