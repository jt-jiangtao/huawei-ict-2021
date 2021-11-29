import router from '@system.router';
import {getUserCollectInfo} from '../../../fetch/article.js';
import storage from '@system.storage';

export default {
    data: {
        articles: [],
        userId: ''
    },
    back(){
        router.back()
    },
    onInit(){
        let that = this
        storage.get({
            key: "userId",
            success: function (data) {
                that.userId = data
                getUserCollectInfo(data).then(data=>{
                    that.articles = JSON.parse(data.data).data
                })
            },
            fail(data){
                console.error(data)
            }
        })
    },
    redirectToDetail(id) {
        router.push({
            uri: 'pages/articleDetail/articleDetail',
            params: {
                id
            }
        });
    },
}
