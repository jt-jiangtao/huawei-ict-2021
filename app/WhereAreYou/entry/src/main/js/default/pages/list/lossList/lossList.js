import router from '@system.router';
import storage from '@system.storage';
import {getLossCollectByUser, getLossChildByUser, getLossParentByUser} from '../../../fetch/lossInfo.js'

export default {
    data: {
        content: [],
        title: '',
        type_in: 1,
        userId: ''
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

                that.type_in = that.type
                if (that.type_in === 1) {
                    that.title = "找孩子"
                    getLossChildByUser(that.userId).then(data=>{
                        that.content = JSON.parse(data.data).data.items
                    })
                }else if (that.type_in === 2) {
                    that.title = "找父母"
                    getLossParentByUser(that.userId).then(data=>{
                        that.content = JSON.parse(data.data).data.items
                    })
                }else{
                    that.title = "收藏信息"
                    getLossCollectByUser(that.userId).then(data=>{
                        that.content = JSON.parse(data.data).data[0].items
                    })
                }
            },
            fail(data){
                console.error(data)
            }
        })
    }
}
