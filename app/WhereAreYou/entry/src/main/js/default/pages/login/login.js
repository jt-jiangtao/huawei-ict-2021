import { HuaweiIdAuthParamsHelper, HuaweiIdAuthManager,AuthHuaweiId, OPENID,  PROFILE, GAMES } from '@hmscore/hms-jsb-account'
import FA from '../../support/fa_request.js'
import {login} from '../../fetch/login.js'
import {storageSet, storageGet} from '../../publicData/index.js'
import router from '@system.router';
import my from '../../pages/homePage/my/my.js'
import fetch from '@system.fetch';
import storage from '@system.storage';
import request from '../../utils/toJavaUserDataSource.js'

export default {
    data: {
        authHuaweiId: null
    },
    login(){
        let that = this
        var signInOption = new HuaweiIdAuthParamsHelper().setProfile().setAuthorizationCode().build();
        HuaweiIdAuthManager.getAuthApi().getSignInIntent(signInOption).then(function(result){
            that.authHuaweiId = result
            console.error(result.getServerAuthCode().replace(/\+/g, '%2B'))
            login(result.getServerAuthCode().replace(/\+/g, '%2B')).then(function(data){
                console.info(data)
                let token = JSON.parse(data.data).data.token
                let userId = JSON.parse(data.data).data.userId
                let imageUrl = JSON.parse(data.data).data.imageUrl
                let username = JSON.parse(data.data).data.username
                storage.set({
                    key: "token",
                    value: token
                })
                storage.set({
                    key: "userId",
                    value: userId
                })
                storage.set({
                    key: "imageUrl",
                    value: imageUrl
                })
                request(userId)
                storage.set({
                    key: "username",
                    value: username
                })
                that.back()
            }).catch(error=>{
                console.error(error)
            })
        }).catch((error)=>{
            console.error("silentSignIn fail");
            console.error(JSON.stringify(error));
        });
    },
    cancelAuthorization(){
//         HuaweiIdAuthManager.getAuthApi方法返回huaweiIdAuth对象，在huaweiIdAuth对象中调用huaweiIdAuth.cancelAuthorization方法
        HuaweiIdAuthManager.getAuthApi().cancelAuthorization().then((result)=>{
//            帐号取消授权成功
            console.info("cancelAuthorization success");
            console.info(JSON.stringify(result));
        }).catch((error) => {
            //帐号取消授权失败
            console.error("cancelAuthorization fail");
            console.error(JSON.stringify(error));
        });
    },
    back(){
        router.back()
        //TODO: 存在一个bug,即登录成功后只有一个view
//        router.replace({
//            uri: 'pages/index/index'
//        });
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
    },
    setData(token, userId, imageUrl, username){
        return new Promise(resolve=>{
                resolve()
        }).then(()=>{
            return new Promise(resolve=>{
                storage.set({
                    key: "token",
                    value: token,
                    complete: function(){
                        resolve()
                    }
                })
            })
        }).then(()=>{
            return new Promise(resolve=>{
                storage.set({
                    key: "userId",
                    value: userId,
                    complete: function(){
                        resolve()
                    }
                })
            })
        }).then(()=>{
            return new Promise(resolve=>{
                storageSet("imageUrl", imageUrl).then(data=>{
                    console.error(data)
                    resolve()
                })
            })
        }).then(()=>{
            return new Promise(resolve=>{
                storageSet("username", username).then(data=>{
                    console.error(data)
                    resolve()
                })
            })
        })
    }
}
