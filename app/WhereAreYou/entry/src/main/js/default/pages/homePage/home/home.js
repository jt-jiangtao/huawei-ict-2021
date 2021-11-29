import router from '@system.router';
import {getSwiperInfo} from '../../../fetch/swiper.js'
import {getSimpleInfo} from '../../../fetch/lossInfo.js'
import storage from '@system.storage';

export default {
    data: {
        swiperInfo: null,
        lossListInfo: null,
        userId: ""
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
    redirectToSearch(){
        router.push({
            uri: "pages/searchPage/searchPage"
        });
    },
    redirect(uri){
      router.push({
          uri
      })
    },
    onTextClick() {
        this.$element("apiMenu").show({x: 365, y: 30});
    },
    onMenuSelected(e){
        if (this.userId.startsWith("user@")) {
            switch(e.value){
                case "1":
                    console.info("here--")
                    router.push({
                        uri: 'pages/find/find',
                        params: {
                            type: "CHILD"
                        }
                    });
                    break;
                case "2":
                    router.push({
                        uri: 'pages/find/find',
                        params: {
                            type: "PARENT"
                        }
                    });
                    break;
            }
        }else{
            router.push({
                uri: 'pages/login/login'
            })
        }
    },
    onPageShow(){
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
    },
    redirectToNotification(){
        if (this.userId.startsWith("user@")) {
            router.push({
                uri: 'pages/list/notificationList/notificationList'
            })
        }else {
            router.push({
                uri: 'pages/login/login'
            })
        }
    }
}
