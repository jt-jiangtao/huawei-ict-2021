import router from '@system.router';

export default {
    data: {
    },
    back(){
        router.back()
    },
    getTitle(){
        if (this.item.type === "SYSTEM") {
            return "系统消息"
        }else if (this.item.type === "FIND") {
            return "数据库匹配成功，请验证"
        }else{
            return "用户提供线索"
        }
    }
}
