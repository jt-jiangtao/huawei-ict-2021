import { HuaweiIdAuthParamsHelper, HuaweiIdAuthManager,AuthHuaweiId, OPENID,  PROFILE, GAMES } from '@hmscore/hms-jsb-account'
import fetch from '@system.fetch';
import CODE from '../../support/request_code.js'
import FA from '../../support/fa_request.js'
import {getLossDetailInfo} from '../../fetch/lossInfo.js'

export default {
    data: {
        authHuaweiId: null
    },
    login(){
//        this.request(CODE.TAKE_PHOTO)
//        fetch.fetch({
//            url: `https://api.jiangtao.website/api/articles/get_simple_info`,
//            success: function(response) {
//                console.info("fetch success");
//                console.info(response)
//            },
//            fail: function(error) {
//                console.info("fetch fail");
//                console.info(error)
//            }
//        });
//        let that = this
//        var signInOption = new HuaweiIdAuthParamsHelper().setProfile().setAuthorizationCode().build();
        // HuaweiIdAuthManager.getAuthApi方法返回huaweiIdAuth对象，在huaweiIdAuth对象中调用huaweiIdAuth.silentSignIn方法
//        HuaweiIdAuthManager.getAuthApi().getSignInIntent(signInOption).then((result)=>{
//            that.authHuaweiId = result
            //登录成功，获取用户的华为帐号信息
//            console.info("silentSignIn success");
//            console.info("openId:" + result.getOpenId());
//            console.info("unionId:" + result.getUnionId());
//            console.info("昵称:" + result.getDisplayName());
//            console.info("头像:" + result.getAvatarUri());
//            console.info("Scope集合:" + result.getAuthorizedScopes());
//            console.info("用户的授权码:" + result.getServerAuthCode());
//            console.info("邮箱地址:" + result.getEmail());
//            console.info("帐号的ID Token:" + result.getIdToken());
//            console.info("用户的名字:" + result.getGivenName());
//            console.info("用户的姓氏:" + result.getFamilyName());
//        }).catch((error)=>{
            //登录失败
//            console.error("silentSignIn fail");
//            console.error(JSON.stringify(error));
//        });
    },
    cancelAuthorization(){
        // HuaweiIdAuthManager.getAuthApi方法返回huaweiIdAuth对象，在huaweiIdAuth对象中调用huaweiIdAuth.cancelAuthorization方法
        HuaweiIdAuthManager.getAuthApi().cancelAuthorization().then((result)=>{
            //帐号取消授权成功
            console.info("cancelAuthorization success");
            console.info(JSON.stringify(result));
        }).catch((error) => {
            //帐号取消授权失败
            console.error("cancelAuthorization fail");
            console.error(JSON.stringify(error));
        });
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
