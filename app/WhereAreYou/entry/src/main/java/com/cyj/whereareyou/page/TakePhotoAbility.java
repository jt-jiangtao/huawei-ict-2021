package com.cyj.whereareyou.page;

import com.cyj.whereareyou.slice.TakePhotoSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class TakePhotoAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(TakePhotoSlice.class.getName());
    }
}
