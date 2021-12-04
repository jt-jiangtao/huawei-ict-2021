import router from '@system.router';
import {getLossDetailInfo, isCollect, collectAdd, collectRemove} from '../../fetch/lossInfo.js'
import storage from '@system.storage';

export default {
    data: {
        lossId: 1,
        detailInfo: {},
        images: [{}],
        isCollect: false,
        refresh: false
    },
    back(){
        router.back()
    },
    onInit(){
        this.lossId = this.id || 3
        let that = this
        getLossDetailInfo(this.lossId).then(function(data){
            that.detailInfo = JSON.parse(data.data).data
            that.images = JSON.parse(data.data).data.images
        }).catch(function(error){
            console.log(error);
        })

        storage.get({
            key: "userId",
            success: function (data) {
                that.userId = data
                isCollect(that.userId, that.lossId).then(data=>{
                    that.isCollect = JSON.parse(data.data).data.status
                })
            },
            fail(data){
                console.error(data)
            }
        })
    },
    chickCollect(){
        let that = this
        if (this.userId.startsWith("user@")) {
            if (this.isCollect) {
                collectRemove(this.userId, this.lossId).then(data=>{
                    if(JSON.parse(data.data).data.status === 1){
                        that.isCollect = false
                    }
                })
            }else{
                collectAdd(this.userId, this.lossId).then(data=>{
                    if(JSON.parse(data.data).data.status === 1){
                        that.isCollect = true
                    }
                })
            }
        }
    },
    provideClew(){
        let that = this
        router.push({
            uri: 'pages/publishClew/publishClew',
            params: {
                to: that.detailInfo.userId
            }
        })
    },
    refreshFunc(e) {
        var that = this;
        that.onInit()
        that.refresh = e.refreshing;
        setTimeout(function(){
            that.refresh = false;
        }, 2000)
    }
}
