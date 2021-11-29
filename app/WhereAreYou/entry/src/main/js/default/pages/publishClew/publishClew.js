import router from '@system.router';
import CODE from '../../support/request_code.js'
import FA from '../../support/fa_request.js'
import request from '@system.request';
import storage from '@system.storage';
import {provideClew} from '../../fetch/clew.js';

export default {
    data: {
        content:'',
        imagesList: [],
        can: false,
        userId: ''
    },
    back(){
        router.back()
    },
    selectImage(){
        this.$element("simpledialog").show();
    },
    takePhoto(){
        this.$element("simpledialog").close();
        this.request(CODE.TAKE_PHOTO)
    },
    selectPhoto(){
        this.$element("simpledialog").close();
        this.request(CODE.SELECT_IMAGE)
    },
    async request(request_code) {
        var actionData = {};
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
            let srcUri = "internal://cache//" + callbackJson.data.abilityEvent.substring(callbackJson.data.abilityEvent.lastIndexOf("\/") + 1, callbackJson.data.abilityEvent.length)
            that.upLoad(srcUri)
        })

        var ret = JSON.parse(result);
        if (ret.code == 0) {
            this.title = JSON.stringify(ret.abilityResult);
            console.info('plus result is:' + JSON.stringify(ret.abilityResult));
        } else {
            console.error('plus error code:' + JSON.stringify(ret.code));
        }
    },
    deleteImage(key){
        this.imagesList.splice(key, 1)
        this.canChange()
    },
    upLoad(uri) {
        let that = this
        request.upload({
            url: 'https://api.jiangtao.website/api/upload/raw',
            files: [
                {
                    uri,
                    name: 'file'
                },
            ],
            success: function(data) {
                that.imagesList.push(JSON.parse(data.data).data.url);
                that.canChange()
            },
            fail: function(data) {
                console.log('upload fail', data);
            },
        });
    },
    onInit(){
        let that = this
        storage.get({
            key: "userId",
            success: function (data) {
                that.userId = data
            },
            fail(data){
                console.error(data)
            }
        })
    },
    contentChange(e){
        this.content = e.value
        this.canChange()
    },
    canChange(){
        if (this.content === '') {
            this.can =  false
        }else {
            this.can = true
        }
    },
    provideClew(){
        if (this.can) {
            let clew = {
                content: this.content,
                images: this.imagesList
            }
            provideClew(this.userId, this.to, JSON.stringify(clew)).then(data=>{
                if (JSON.parse(data.data).data.status === "200"){
                    router.back()
                }
            })
        }
    }
}
