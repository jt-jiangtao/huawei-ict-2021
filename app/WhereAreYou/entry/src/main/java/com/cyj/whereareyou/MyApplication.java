package com.cyj.whereareyou;

import com.huawei.hms.jsb.adapter.har.bridge.HmsBridge;
import ohos.aafwk.ability.AbilityPackage;

public class MyApplication extends AbilityPackage {
    private HmsBridge mHmsBridge;
    @Override
    public void onInitialize() {
        // 初始化JSB
        mHmsBridge = HmsBridge.getInstance();
        mHmsBridge.initBridge(this);
        super.onInitialize();
    }

    @Override
    public void onEnd() {
        // 结束JSB
        mHmsBridge.destoryBridge();
        super.onEnd();
    }
}
