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
            return "数据库匹配成功，请验证"
        }else{
            return "用户提供线索"
        }
    },
    onInit(){
        this.jsonData = JSON.parse(this.item.content)
//        this.jsonData = JSON.parse('{"content":"是不是这个","images":["https://image.jiangtao.website/raw/1d38d433-b90f-4d29-a2c8-de8675ac8a46.jpg","https://image.jiangtao.website/raw/1d38d433-b90f-4d29-a2c8-de8675ac8a46.jpg"],"contacts":[{"id": 6, "name": "陈杰-家人", "phone": "17836363635", "location": "云南省昆明市官渡区吴井路617号", "relation": "奶奶"}, {"id": 7, "name": "王梓漫-家属", "phone": "19836363663", "location": "广东省潮州市湘桥区绿茵路", "relation": "家属"},{"id": 8, "name": "李敏-母亲", "phone": "15637378383", "location": "陕西省咸阳市兴平市", "relation": "母亲"}]}')
    }
}
