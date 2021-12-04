import router from '@system.router';
import {userGUID} from '../../../utils/uuid.js'
import storage from '@system.storage';
import {verify} from '../../../fetch/login.js';
import request from '../../../utils/toJavaUserDataSource.js';
import {getUserCollectNumber} from '../../../fetch/article.js'
import {getSimpleInfoNumber, collectNumber} from '../../../fetch/lossInfo.js'
import {unSeen} from '../../../fetch/clew.js'

export default {
    data: {
        isLog: false,
        imageUrl: null,
        username: "",
        token: "",
        userId: '',
        collect_article_num: 0,
        findParentNumber: 0,
        findChildrenNumber: 0,
        collect_event_num: 0,
        hasMessage: true,
        refreshM: false
    },
    toLogPage() {
        router.push({
            uri: 'pages/login/login'
        });
    },
    toSettingPage() {
        if (this.userId.startsWith("user@")) {
            router.push({
                uri: 'pages/settings/settings'
            })
        }else {
            router.push({
                uri: 'pages/login/login'
            })
        }
    },
    redirect(uri) {
        if (this.userId.startsWith("user@")) {
            router.push({
                uri
            })
        }else {
            router.push({
                uri: 'pages/login/login'
            })
        }
    },
    redirectToLossList(type) {
        if (this.userId.startsWith("user@")) {
            router.push({
                uri: 'pages/list/lossList/lossList',
                params: {
                    type
                }
            })
        }else {
            router.push({
                uri: 'pages/login/login'
            })
        }
    },
    onPageShow() {
        this.refresh()
    },
    refresh() {
        let that = this
        that.hasMessage = false
        storage.get({
            key: "userId",
            success: function (data) {
                if (data === "") {
                    let uuid = userGUID()
                    that.userId = uuid
                    request(uuid)
                    storage.set({
                        key: "userId",
                        value: uuid
                    })
                } else {
                    that.userId = data
                    getUserCollectNumber(data).then(data=>{
                        that.collect_article_num = JSON.parse(data.data).data.number || 0
                    })
                    getSimpleInfoNumber(data).then(data=>{
                        that.findChildrenNumber = JSON.parse(data.data).data.findChildrenNumber || 0;
                        that.findParentNumber = JSON.parse(data.data).data.findParentNumber || 0;
                    })
                    collectNumber(data).then(data=>{
                        that.collect_event_num = JSON.parse(data.data).data.status || 0;
                    })
                    if (data.startsWith("user@")){
                        unSeen(data).then(data=>{
                            if(JSON.parse(data.data).data !== 0){
                                that.hasMessage = true
                            }else{
                                that.hasMessage = false
                            }
                        })
                    }
                    request(data)
                }
            },
            fail(data) {
                console.error(data)
            }
        })
        storage.get({
            key: "username",
            success: function (data) {
                that.username = data
            },
            fail(data) {
                console.error(data)
            }
        })
        storage.get({
            key: "imageUrl",
            success: function (data) {
                that.imageUrl = data
            },
            fail(data) {
                console.error(data)
            }
        })
        storage.get({
            key: "token",
            success: function (data) {
                that.token = data
                if (data !== '') {
                    verify(data).then(data => {
                        if (JSON.parse(data.data).data.status === "200") that.isLog = true;
                    })
                }
            },
            fail(data) {
                console.error(data)
            }
        })
    },
    manageContact() {
        if (this.userId.startsWith("user@")) {
            router.push({
                uri: 'pages/list/contactList/contactList'
            })
        }else {
            router.push({
                uri: 'pages/login/login'
            })
        }
    },
    refreshFunc(e) {
        var that = this;
        that.refresh()
        that.refreshM = e.refreshing;
        setTimeout(function(){
            that.refreshM = false;
        }, 2000)
    }
}