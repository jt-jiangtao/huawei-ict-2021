package com.cyj.whereareyou;

import com.amap.api.maps.MapsInitializer;
import com.amap.api.services.core.ServiceSettings;
import com.huawei.hms.jsb.adapter.har.bridge.HmsBridge;
import ohos.aafwk.ability.AbilityPackage;

public class MyApplication extends AbilityPackage {
    private HmsBridge mHmsBridge;
    @Override
    public void onInitialize() {
        String key = "d654137553104fc3698fca04f18a5cee";
        ServiceSettings.getInstance().setApiKey(key);
        MapsInitializer.setApiKey(key);
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
