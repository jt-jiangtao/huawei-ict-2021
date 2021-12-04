package com.cyj.whereareyou.slice;

import com.cyj.whereareyou.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.window.service.WindowManager;
import ohos.app.Environment;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.IntentConstants;
import ohos.utils.net.Uri;

import java.io.File;
import java.io.IOException;
import java.nio.file.spi.FileSystemProvider;

public class SelectImageAbilitySlice extends AbilitySlice {
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0001, "选择图片测试");
    private String filename;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_main_camera_slice);
    }

    @Override
    public void onActive() {
        super.onActive();
        selectPic();
    }

    private void selectPic() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder().withAction(IntentConstants.ACTION_CHOOSE) .build();
        intent.addFlags(Intent.FLAG_NOT_OHOS_COMPONENT);
        intent.setType("image/*");
        intent.setOperation(operation);
        startAbilityForResult(intent, 100);
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }

}
