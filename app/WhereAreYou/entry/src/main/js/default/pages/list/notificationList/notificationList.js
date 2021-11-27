import router from '@system.router';
import CODE from '../../../support/request_code.js'
import FA from '../../../support/fa_request.js'

export default {
    data: {

    },
    back(){
        router.back()
    },
    onInit(){
        this.request()
    },
    async request() {
        var actionData = {};
        var action = {};
        action.bundleName = 'com.cyj.whereareyou';
        action.abilityName = 'com.cyj.whereareyou.service.NotificationBridge';
        action.messageCode = 1001;
        action.data = actionData;
        action.abilityType = FA.ABILITY_TYPE_EXTERNAL;
        action.syncOption = FA.ACTION_SYNC;

        var result = await FeatureAbility.callAbility(action);
        var ret = JSON.parse(result);
        if (ret.code == 0) {
            console.info('plus result is:' + JSON.stringify(ret.abilityResult));
        } else {
            console.error('plus error code:' + JSON.stringify(ret.code));
        }
    },
}
