import router from '@system.router';
import {getSwiperInfo} from '../../../fetch/swiper.js'
import {getSimpleInfo} from '../../../fetch/lossInfo.js'

export default {
    data: {
        swiperInfo: null,
        lossListInfo: null
    },
    redirectToSwiperDetail(url){
        console.info(url)
        router.push({
            uri: url
        });
    },
    redirectToDetail(param) {
        console.info(param)
        router.push({
            uri: 'pages/infoDetail/infoDetail',
            params: {
                id: param
            }
        });
    },
    onTextClick() {
        this.$element("apiMenu").show({x: 365, y: 30});
    },
    onMenuSelected(e){
        switch(e.value){
            case "1":
                console.info("here--")
                router.push({
                    uri: 'pages/login/login'
                });
                break;
            case "2":
                router.push({
                    uri: 'pages/publish/findParent/findParent'
                });
                break;
        }
    },
    onInit(){
        let that = this
        getSwiperInfo().then(function(data){
            console.info("Success-->")
            that.swiperInfo = JSON.parse(data.data).data
            console.log(that.swiperInfo);
        }).catch(function(error){
            console.info("fail-->")
            console.log(error);
        })
        getSimpleInfo().then(function(data){
            console.info("Success-->")
            that.lossListInfo = JSON.parse(data.data).data.items
            console.log(JSON.stringify(that.lossListInfo));
        }).catch(function(error){
            console.info("fail-->")
            console.log(error);
        })
    }
}
