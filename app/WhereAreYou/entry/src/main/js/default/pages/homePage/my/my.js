import router from '@system.router';
import {userGUID} from '../../../utils/uuid.js'
import storage from '@system.storage';
import {verify} from '../../../fetch/login.js';
import request from '../../../utils/toJavaUserDataSource.js';

export default {
    data: {
        isLog: false,
        imageUrl: null,
        username: "",
        token: "",
        userId: ''
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
    }
}