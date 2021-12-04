import router from '@system.router';

export default {
    data: {
        jsonData: {}
    },
    back(){
        router.back()
    },
    getTitle(){
        if (this.item.type === "SYSTEM") {
            return "系统消息"
        }else if (this.item.type === "FIND") {
            return "数据库匹配成功"
        }else{
            return "用户提供线索"
        }
    },
    onInit(){
        if (this.item.content === undefined) {
            this.jsonData = JSON.parse(JSON.parse(this.item).content)
        }else{
            this.jsonData = JSON.parse(this.item.content)
        }
    },
    detail(){
        let that = this
        router.push({
            uri: "pages/infoDetail/infoDetail",
            params: {
                id: JSON.parse(that.item.content).loss
            }
        })
    }
}
