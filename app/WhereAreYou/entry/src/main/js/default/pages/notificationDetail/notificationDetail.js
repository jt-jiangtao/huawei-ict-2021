import router from '@system.router';
import {seen} from '../../fetch/notification.js';

export default {
    data: {
        jsonData: {},
        time: ''
    },
    back(){
        router.back()
    },
    getTitle(){
        if (this.item.type === "SYSTEM") {
            return "系统消息"
        }else if (this.item.type === "FIND") {
            return "数据库匹配成功"
        }else{
            return "用户提供线索"
        }
    },
    onInit(){
        if (this.item.content === undefined) {
            this.jsonData = JSON.parse(JSON.parse(this.item).content)
            seen(JSON.parse(this.item).id).then(data=>{
                console.log(data)
            }).catch(error=>{
                console.log(error)
            })
        }else{
            this.jsonData = JSON.parse(this.item.content)
            seen(this.item.id).then(data=>{
                console.log(data)
            }).catch(error=>{
                console.log(error)
            })
        }
        this.time = this.jsonData.time
    },
    detail(){
        let that = this
        router.push({
            uri: "pages/infoDetail/infoDetail",
            params: {
                id: that.jsonData.loss
            }
        })
    }
}
