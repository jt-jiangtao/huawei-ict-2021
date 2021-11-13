import { HuaweiIdAuthParamsHelper, HuaweiIdAuthManager, OPENID,  PROFILE, GAMES } from '@hmscore/hms-jsb-account'
import CODE from '../../support/request_code.js'
import FA from '../../support/fa_request.js'

export default {
    data: {
        title: "",
        image_url: ""
    },
    onInit() {
        this.title = this.$t('strings.world');
    },
    login(){
        this.request(CODE.SELECT_IMAGE)
    },
    request: async function(request_code) {
        var actionData = {};
        actionData.firstNum = 1024;
        actionData.secondNum = 2048;

        var action = {};
        action.bundleName = 'com.cyj.whereareyou';
        action.abilityName = 'com.cyj.whereareyou.service.OpenUIService';
        action.messageCode = request_code;
        action.data = actionData;
        action.abilityType = FA.ABILITY_TYPE_EXTERNAL;
        action.syncOption = FA.ACTION_SYNC;

        let that = this
        var result = await FeatureAbility.subscribeAbilityEvent(action, function(callbackData) {
            var callbackJson = JSON.parse(callbackData);
            console.info('eventData is: ' + JSON.stringify(callbackJson.data));
            that.image_url = callbackJson.data.abilityEvent
        })

        var ret = JSON.parse(result);
        if (ret.code == 0) {
            this.title = JSON.stringify(ret.abilityResult);
            console.info('plus result is:' + JSON.stringify(ret.abilityResult));
        } else {
            console.error('plus error code:' + JSON.stringify(ret.code));
        }
    }
}
