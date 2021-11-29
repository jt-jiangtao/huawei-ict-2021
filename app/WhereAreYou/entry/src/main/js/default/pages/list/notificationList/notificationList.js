import router from '@system.router';
import storage from '@system.storage';
import {getUserNotification} from '../../../fetch/notification.js';
import {seen} from '../../../fetch/notification.js';

export default {
    data: {
        userId: '',
        notifications: []
    },
    back(){
        router.back()
    },
    onShow(){
        let that = this
        storage.get({
            key: "userId",
            success: function (data) {
                that.userId = data
                if(data !== '' && !data.startsWith("visitor@")){
                    getUserNotification(data).then(data=>{
                        that.notifications = JSON.parse(data.data).data
                    })
                }else {
                    that.notifications = []
                }
            },
            fail(data){
                console.error(data)
            }
        })
    },
    getTitle(type){
        if (type === "SYSTEM") {
            return "系统消息"
        }else if (type === "FIND") {
            return "数据库匹配成功，请验证"
        }else{
            return "用户提供线索"
        }
    },
    detail(item){
        seen(item.id).then(data=>{
            console.log(data)
        }).catch(error=>{
            console.log(error)
        })
        router.push({
            uri: 'pages/notificationDetail/notificationDetail',
            params: {
                item
            }
        })
    }
}
