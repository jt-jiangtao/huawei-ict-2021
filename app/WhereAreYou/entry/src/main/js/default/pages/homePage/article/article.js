import router from '@system.router';
import {getArticleSimpleInfo} from '../../../fetch/article.js'

export default {
    data: {
        items: [{}]
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
    }
}
