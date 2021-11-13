package com.cyj.whereareyou.page;

import com.cyj.whereareyou.slice.SelectImageAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.app.Environment;
import ohos.bundle.AbilityInfo;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.photokit.metadata.AVStorage;
import ohos.utils.net.Uri;

import java.io.*;
import java.util.ArrayList;

public class SelectImageAbility extends Ability {
    private String filename;
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0001, "选择图片测试");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(SelectImageAbilitySlice.class.getName());
    }

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == 100){
            //定义数据能力帮助对象
            DataAbilityHelper helper=DataAbilityHelper.creator(getContext());

            FileDescriptor fd = null;
            String filename = "";
            if (resultData == null) {
                terminateAbility();
                return;
            }
            try {
                fd = helper.openFile(resultData.getUri(), "r");
                FileInputStream fis = new FileInputStream(fd);
                byte[] b = new byte[fis.available()];
                fis.read(b);
                File saveFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsoluteFile(), "IMG_" + System.currentTimeMillis() + ".jpg");
                filename = saveFile.getAbsolutePath();
                FileOutputStream output = new FileOutputStream(saveFile);
                output.write(b);
                output.flush();
            } catch (IOException | DataAbilityRemoteException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withDeviceId("")
                    .withBundleName("com.cyj.whereareyou")
                    .withAbilityName("com.cyj.whereareyou.service.OpenUIService")
                    .build();
            intent.setOperation(operation);
            intent.setParam("img", filename);
            intent.setParam("code", 1001);
            startAbility(intent);
            terminateAbility();
        }
        super.onAbilityResult(requestCode, resultCode, resultData);
    }
}
