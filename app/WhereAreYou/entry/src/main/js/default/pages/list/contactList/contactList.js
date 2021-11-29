import router from '@system.router';
import storage from '@system.storage';
import {getContact} from '../../../fetch/contact.js';

export default {
    data: {
        contacts: [],
        userId: ''
    },
    back(){
        router.back()
    },
    onShow(){
        let that = this
        storage.get({
            key: "userId",
            success: function (data) {
                that.userId = data
                getContact(data).then(data=>{
                    that.contacts = JSON.parse(data.data).data
                })
            },
            fail(data){
                console.error(data)
            }
        })
    },
    edit(item){
        router.push({
            uri: 'pages/contactEdit/contactEdit',
            params: {
                item,
                id: item.id,
                operation: "EDIT"
            }
        })
    },
    add(){
        router.push({
            uri: 'pages/contactEdit/contactEdit',
            params: {
                operation: "ADD"
            }
        })
    }
}
