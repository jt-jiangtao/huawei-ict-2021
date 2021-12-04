import router from '@system.router';
import {getSwiperInfo} from '../../../fetch/swiper.js'
import {getSimpleInfo} from '../../../fetch/lossInfo.js'
import {unSeen} from '../../../fetch/clew.js'
import storage from '@system.storage';
import prompt from '@system.prompt';

export default {
    data: {
        swiperInfo: null,
        lossListInfo: null,
        userId: "",
        hasMessage: true,
        refresh: false
    },
    redirectToSwiperDetail(url){
        router.push({
            uri: url.split("?")[0],
            params: {
                id: url.split("?")[1]
            }
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
        that.hasMessage = false
        storage.get({
            key: "userId",
            success: function (data) {
                that.userId = data
                if (data.startsWith("user@")){
                    unSeen(data).then(data=>{
                        if(JSON.parse(data.data).data !== 0){
                            that.hasMessage = true
                        }else{
                            that.hasMessage = false
                        }
                    })
                }
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
    },
    refreshFunc(e) {
        var that = this;
        that.onInit()
        that.onPageShow()
        that.refresh = e.refreshing;
        setTimeout(function(){
            that.refresh = false;
        }, 2000)
    }
}
