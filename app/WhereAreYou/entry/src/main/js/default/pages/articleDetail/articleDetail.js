import router from '@system.router';
import {getArticleDetailInfo} from '../../fetch/article.js'

export default {
    data: {
        articleId: 1,
        detailInfo: {}
    },
    backLast(){
        router.back()
    },
    onInit(){
        this.articleId = this.id || 1
        let that = this
        getArticleDetailInfo(this.articleId).then(data=>{
            that.detailInfo = JSON.parse(data.data).data
        }).catch(error=>{
            console.error(error)
        })
    }
}
