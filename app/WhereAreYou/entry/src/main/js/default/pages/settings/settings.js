import router from '@system.router';
import {HuaweiIdAuthParamsHelper, HuaweiIdAuthManager, AuthHuaweiId, OPENID, PROFILE, GAMES
} from '@hmscore/hms-jsb-account'
import storage from '@system.storage';
import prompt from '@system.prompt';

export default {
    data: {},
    back() {
        router.back()
    },
    onInit() {

    },
    cancelRegister() {
        this.$element("cancel-dialog").show()
    },
    logout() {
        this.$element("out-dialog").show()
    },
    cancelCD() {
        this.$element("cancel-dialog").close()
    },
    cancelOut() {
        this.$element("out-dialog").close()
    },
    setCD() {
        HuaweiIdAuthManager.getAuthApi().cancelAuthorization().then((result) => {
            // 帐号取消授权成功
            console.info("cancelAuthorization success");
            console.info(JSON.stringify(result));
            this.$element("cancel-dialog").close()
            prompt.showToast({
                message: "注销成功",
                duration: 3000,
            });
            let that = this
//            setInterval(function(){
//                that.setOut()
//            }, 3000)
            that.setOut()
        }).catch((error) => {
            // 帐号取消授权失败
            console.error("cancelAuthorization fail");
            console.error(JSON.stringify(error));
        });
    },
    setOut() {
        storage.clear({
            success: function() {
                console.log('call storage.clear success.');
                router.back()
            },
            fail: function(data, code) {
                console.log('call storage.clear fail, code: ' + code + ', data: ' + data);
            },
        });
    }
}
