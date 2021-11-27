package com.cyj.whereareyou.data;

import com.cyj.whereareyou.data.entity.Notification;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.content.Intent;
import ohos.data.DatabaseHelper;
import ohos.data.dataability.DataAbilityUtils;
import ohos.data.rdb.*;
import ohos.data.resultset.ResultSet;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.MessageParcel;
import ohos.utils.net.Uri;
import ohos.utils.PacMap;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class NotificationData extends Ability {
    private static final String TAG = NotificationData.class.getSimpleName();

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");

    private final StoreConfig config = StoreConfig.newDefaultConfig(Notification.DB_NAME);

    private RdbStore rdbStore;

    private final RdbOpenCallback rdbOpenCallback = new RdbOpenCallback() {
        @Override
        public void onCreate(RdbStore store) {
            store.executeSql(
                    "create table if not exists " + Notification.DB_TAB_NAME + " (id integer primary key autoincrement, "
                            + Notification.DB_COLUMN_TITLE + " text not null, " + Notification.DB_COLUMN_CONTENT + " text not null, "
                            + Notification.DB_COLUMN_TYPE + " text not null, "
                            + Notification.DB_COLUMN_TIME + " text not null, " + Notification.DB_COLUMN_READ_STATUS + " integer default 0)");
            HiLog.info(LABEL_LOG, "%{public}s", "create a  new database");
        }

        @Override
        public void onUpgrade(RdbStore store, int oldVersion, int newVersion) {
            HiLog.info(LABEL_LOG, "%{public}s", "DataBase upgrade");
        }
    };

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "NotificationData onStart");
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        rdbStore = databaseHelper.getRdbStore(config, 1, rdbOpenCallback, null);
    }

    @Override
    public ResultSet query(Uri uri, String[] columns, DataAbilityPredicates predicates) {
        RdbPredicates rdbPredicates = DataAbilityUtils.createRdbPredicates(predicates, Notification.DB_TAB_NAME);
        return rdbStore.query(rdbPredicates, columns);
    }

    @Override
    public int insert(Uri uri, ValuesBucket value) {
        HiLog.info(LABEL_LOG, "NotificationData insert");
        String path = uri.getLastPath();
        if (!"notification".equals(path)) {
            HiLog.info(LABEL_LOG, "%{public}s", "DataAbility insert path is not matched");
            return -1;
        }

        ValuesBucket values = new ValuesBucket();
        values.putString(Notification.DB_COLUMN_TITLE, value.getString(Notification.DB_COLUMN_TITLE));
        values.putString(Notification.DB_COLUMN_CONTENT, value.getString(Notification.DB_COLUMN_CONTENT));
        values.putString(Notification.DB_COLUMN_TIME, value.getString(Notification.DB_COLUMN_TIME));
        values.putString(Notification.DB_COLUMN_TYPE, value.getString(Notification.DB_COLUMN_TYPE));
        int index = (int) rdbStore.insert(Notification.DB_TAB_NAME, values);
        DataAbilityHelper.creator(this).notifyChange(uri);
        return index;
    }

    @Override
    public int delete(Uri uri, DataAbilityPredicates predicates) {
        RdbPredicates rdbPredicates = DataAbilityUtils.createRdbPredicates(predicates, Notification.DB_TAB_NAME);
        int index = rdbStore.delete(rdbPredicates);
        HiLog.info(LABEL_LOG, "%{public}s", "delete");
        DataAbilityHelper.creator(this).notifyChange(uri);
        return index;
    }

    @Override
    public int update(Uri uri, ValuesBucket value, DataAbilityPredicates predicates) {
        RdbPredicates rdbPredicates = DataAbilityUtils.createRdbPredicates(predicates, Notification.DB_TAB_NAME);
        int index = rdbStore.update(value, rdbPredicates);
        HiLog.info(LABEL_LOG, "%{public}s", "update");
        DataAbilityHelper.creator(this).notifyChange(uri);
        return index;
    }

    @Override
    public FileDescriptor openFile(Uri uri, String mode) {
        File file = new File(getFilesDir(), uri.getDecodedQuery());
        if (!"rw".equals(mode)) {
            boolean result = file.setReadOnly();
            HiLog.info(LABEL_LOG, "%{public}s", "setReadOnly result: " + result);
        }
        FileDescriptor fileDescriptor = null;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileDescriptor = fileInputStream.getFD();
            return MessageParcel.dupFileDescriptor(fileDescriptor);
        } catch (IOException ioException) {
            HiLog.error(LABEL_LOG, "%{public}s", "openFile: ioException");
        }
        return fileDescriptor;
    }

}