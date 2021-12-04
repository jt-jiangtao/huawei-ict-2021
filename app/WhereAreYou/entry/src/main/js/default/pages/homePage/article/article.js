import router from '@system.router';
import {getArticleSimpleInfo} from '../../../fetch/article.js'

export default {
    data: {
        items: [{}],
        refresh: false
    },
    redirectToDetail(id) {
        router.push({
            uri: 'pages/articleDetail/articleDetail',
            params: {
                id
            }
        });
    },
    back(){
        router.back();
    },
    onInit(){
        let that = this
        getArticleSimpleInfo().then(data=>{
            that.items = JSON.parse(data.data).data.items
            console.error(JSON.stringify(that.items))
        }).catch(error=>{
            console.error(error)
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
