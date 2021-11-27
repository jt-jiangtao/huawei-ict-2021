import router from '@system.router';
import dateFormat from '../../utils/date.js';
import CODE from '../../support/request_code.js'
import FA from '../../support/fa_request.js'
import request from '@system.request';
import file from '@system.file';
import {getContact} from '../../fetch/contact.js'
import storage from '@system.storage';
import {commitInfo} from '../../fetch/lossInfo.js';
import prompt from '@system.prompt';

export default {
    back(){
        router.back()
    },
    data: {
        i: 0,
        label_1:
        {
            prevLabel: '上一项',
            nextLabel: '下一项',
            status: 'disabled'
        },
        label_2:
        {
            prevLabel: '上一项',
            nextLabel: '下一项',
            status: 'disabled'
        },
        label_3:
        {
            prevLabel: '上一项',
            nextLabel: '下一项',
            status: 'disabled'
        },
        label_4:
        {
            prevLabel: '上一项',
            nextLabel: '下一项',
            status: 'disabled'
        },
        label_5:
        {
            prevLabel: '上一项',
            nextLabel: '提交',
            status: 'normal'
        },
        label_6:
        {
            prevLabel: '上一项',
            nextLabel: '提交',
            status: 'normal'
        },
        titleList: [],
        datetimevalue: "2021-10-23 10:45",
        imagesList: [],
        needContact: true,
        normalInfo: {
            name: null,
            age: null,
            lossLocation: null,
            sex: null,
            police: null
        },
        detailDes: '',
        eventDes: '',
        contactInfo: {
            relation: '',
            name: '',
            phone: '',
            location: ''
        },
        userId: ''
    },
    onInit(){
        let that = this
        storage.get({
            key: "userId",
            success(data) {
                that.userId = data
                getContact(data).then(data=>{
                    that.needContact = (JSON.parse(data.data).data.length <= 0) ? true : false;
                    if (that.needContact) {
                        that.titleList = ['基本信息', '孩子照片', '详细特征', '事件描述', '联系信息', '信息确认']
                    }else{
                        that.titleList = ['基本信息', '孩子照片', '详细特征', '事件描述', '信息确认']
                    }
                }).catch(error=>{
                    console.log("error")
                })
                that.datetimevalue = dateFormat(new Date())
            },
            fail(data){
                console.error(data)
            }
        })
    },
    initData(data){
        let that = this
        getContact(data).then(data=>{
            that.needContact = (JSON.parse(data.data).data.length <= 0) ? true : false;
            if (that.needContact) {
                that.titleList = ['基本信息', '孩子照片', '详细特征', '事件描述', '联系信息', '信息确认']
            }else{
                that.titleList = ['基本信息', '孩子照片', '详细特征', '事件描述', '信息确认']
            }
        })
        that.datetimevalue = dateFormat(new Date())
    },
    nextClick(e) {
        var index = {
            pendingIndex: e.pendingIndex
        }
        switch(e.pendingIndex){
            case 0:
                this.changeNormalNextStatus()
                break;
            case 1:
                this.changeImageNextStatus()
                break;
            case 2:
                this.detailDesNextStatus()
                break;
            case 3:
                this.eventDesNextStatus()
                break;
            case 4:
                if (this.needContact) {
                    this.contactNextStatus()
                }
                break;
        }

        this.i = e.pendingIndex
        if (e.pendingIndex === 6 && !this.needContact) {
            this.finishStepper()
        }
        return index;
    },
    finishStepper(){
        commitInfo(this.normalInfo.age,
            this.datetimevalue,
            this.normalInfo.lossLocation,
            this.normalInfo.police,
            this.normalInfo.name,
            this.normalInfo.sex,
            this.detailDes,
            this.eventDes,
            this.userId,
            this.imagesList,
            this.contactInfo,
            this.type
        ).then(data=>{
            console.log(data)
            router.back()
            prompt.showToast({
                message: "上传成功,请耐心等待结果~",
                duration: 3000,
            });
        }).catch(error=>{
            console.log(error)
        })
    },
    backClick(e) {
        var index = {
            pendingIndex: e.pendingIndex
        }
        switch(e.pendingIndex){
            case 0:
                this.changeNormalNextStatus()
                break;
            case 1:
                this.changeImageNextStatus()
                break;
            case 2:
                this.detailDesNextStatus()
                break;
            case 3:
                this.eventDesNextStatus()
                break;
            case 4:
                if (this.needContact) {
                    this.contactNextStatus()
                }
                break;

        }
        this.i = e.pendingIndex
        return index;
    },
    datetimeonchange(e) {
        this.datetimevalue=e.year+"-"+(e.month+1)+"-"+e.day+" "+e.hour+":"+e.minute;
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
        this.changeImageNextStatus()
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
                that.changeImageNextStatus()
            },
            fail: function(data) {
                console.log('upload fail', data);
            },
        });
    },
    normalInfoCanNext(){
        if(this.normalInfo.name === null || this.normalInfo.name === '') return false
        else if(this.normalInfo.age === null || this.normalInfo.age === '') return false
        else if(this.normalInfo.lossLocation === null || this.normalInfo.lossLocation === '') return false
        else if(this.normalInfo.sex === null || this.normalInfo.sex === '') return false
        else if(this.normalInfo.police === null || this.normalInfo.police === '') return false
        else return true
    },
    contactInfoCanNext(){
        if(this.contactInfo.name === null || this.contactInfo.name === '') return false
        else if(this.contactInfo.location === null || this.contactInfo.location === '') return false
        else if(this.contactInfo.phone === null || this.contactInfo.phone === '') return false
        else if(this.contactInfo.relation === null || this.contactInfo.relation === '') return false
        else return true
    },
    changeNormalNextStatus(){
        if (this.normalInfoCanNext()) {
            this.$element('my-stepper').setNextButtonStatus({status: 'normal'});
        }else {
            this.$element('my-stepper').setNextButtonStatus({status: 'disabled'});
        }
    },
    detailDesNextStatus(){
        if (this.detailDes !== '') {
            this.$element('my-stepper').setNextButtonStatus({status: 'normal'});
        }else {
            this.$element('my-stepper').setNextButtonStatus({status: 'disabled'});
        }
    },
    eventDesNextStatus(){
        if (this.eventDes !== '') {
            this.$element('my-stepper').setNextButtonStatus({status: 'normal'});
        }else {
            this.$element('my-stepper').setNextButtonStatus({status: 'disabled'});
        }
    },
    contactNextStatus(){
        if (this.contactInfoCanNext()) {
            this.$element('my-stepper').setNextButtonStatus({status: 'normal'});
        }else {
            this.$element('my-stepper').setNextButtonStatus({status: 'disabled'});
        }
    },
    changeImageNextStatus(){
        if (this.imagesList.length >= 1) {
            this.$element('my-stepper').setNextButtonStatus({status: 'normal'});
        }else {
            this.$element('my-stepper').setNextButtonStatus({status: 'disabled'});
        }
    },
    nameChange(e){
        this.normalInfo.name = e.value
        console.log(this.normalInfo.name);
        this.changeNormalNextStatus()
    },
    ageChange(e){
        this.normalInfo.age = e.value
        console.log(this.normalInfo.age)
        this.changeNormalNextStatus()
    },
    locationChange(e){
        this.normalInfo.lossLocation = e.value
        console.log(this.normalInfo.lossLocation)
        this.changeNormalNextStatus()
    },
    sexChange(e){
        this.normalInfo.sex = e.value
        console.log(this.normalInfo.sex)
        this.changeNormalNextStatus()
    },
    policeChange(e){
        this.normalInfo.police = e.value
        console.log(this.normalInfo.police)
        this.changeNormalNextStatus()
    },
    detailDesChange(e){
        this.detailDes = e.value
        this.detailDesNextStatus()
    },
    eventDesChange(e){
        this.eventDes = e.value
        this.eventDesNextStatus()
    },
    relationChange(e){
        this.contactInfo.relation = e.value
        this.contactNextStatus()
    },
    nameCChange(e){
        this.contactInfo.name = e.value
        this.contactNextStatus()
    },
    phoneChange(e){
        this.contactInfo.phone = e.value
        this.contactNextStatus()
    },
    locationCChange(e){
        this.contactInfo.location = e.value
        this.contactNextStatus()
    }
}
