import router from '@system.router';
import {getArticleDetailInfo, getArticleLikeCollectInfo, addLike, removeLike, addCollect, removeCollect} from '../../fetch/article.js'
import storage from '@system.storage';

export default {
    data: {
        articleId: 1,
        detailInfo: {},
        likeNum: 0,
        collectNum: 0,
        isLike: 0,
        isCollect: 0,
        userId: ''
    },
    backLast(){
        router.back()
    },
    onInit(){
        let that = this
        this.articleId = this.id || 1
        storage.get({
            key: "userId",
            success: function (data) {
                that.userId = data
            },
            fail(data){
                console.error(data)
            }
        })
        getArticleDetailInfo(this.articleId).then(data=>{
            that.detailInfo = JSON.parse(data.data).data
            getArticleLikeCollectInfo(this.articleId, this.userId).then(data=>{
                let info = JSON.parse(data.data).data
                that.likeNum = info.like_num
                that.collectNum = info.collect_num
                that.isLike = info.is_like
                that.isCollect = info.is_collect
            })
        }).catch(error=>{
            console.error(error)
        })
    },
    clickLike(){
        if (this.userId.startsWith("user@")) {
            let that = this
            if (this.isLike) {
                removeLike(this.articleId, this.userId).then(data=>{
                    let status = JSON.parse(data.data).data.status
                    if(status === 1){
                        that.isLike = 0
                        that.likeNum --
                    }
                })
            }else{
                addLike(this.articleId, this.userId).then(data=>{
                    let status = JSON.parse(data.data).data.status
                    if(status === 1){
                        that.isLike = 1
                        that.likeNum ++
                    }
                })
            }
        }
    },
    clickCollect(){
        if (this.userId.startsWith("user@")) {
            let that = this
            if (this.isCollect) {
                removeCollect(this.articleId, this.userId).then(data=>{
                    let status = JSON.parse(data.data).data.status
                    if(status === 1){
                        that.isCollect = 0
                        that.collectNum --
                    }
                })
            }else{
                addCollect(this.articleId, this.userId).then(data=>{
                    let status = JSON.parse(data.data).data.status
                    if(status === 1){
                        that.isCollect = 1
                        that.collectNum ++
                    }
                })
            }
        }
    }
}
