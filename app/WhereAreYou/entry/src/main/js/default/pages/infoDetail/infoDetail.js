import router from '@system.router';
import {getLossDetailInfo} from '../../fetch/lossInfo.js'

export default {
    data: {
        lossId: 1,
        detailInfo: {},
        images: [{}]
    },
    back(){
        router.back()
    },
    onInit(){
        this.lossId = this.id || 1
        let that = this
        getLossDetailInfo(this.lossId).then(function(data){
            console.info("Success-->")
            console.info("-->|",data)
            that.detailInfo = JSON.parse(data.data).data
            that.images = JSON.parse(data.data).data.images
        }).catch(function(error){
            console.info("fail-->")
            console.log(error);
        })
    }
}
