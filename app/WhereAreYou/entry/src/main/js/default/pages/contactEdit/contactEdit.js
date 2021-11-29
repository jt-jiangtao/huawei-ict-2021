import router from '@system.router';
import storage from '@system.storage';
import {updateContact, addContact, removeContact} from '../../fetch/contact.js';

export default {
    data: {
        relation: '',
        name: '',
        phone: '',
        location: '',
        can: false,
        userId: ''
    },
    back(){
        router.back()
    },
    canNext(){
        if (this.relation === '') {
            this.can = false
        } else if (this.name === '') {
            this.can =  false
        }else if (this.phone === '') {
            this.can =  false
        }else if (this.location === '') {
            this.can =  false
        }else this.can =  true
    },
    relationChange(e){
        this.relation = e.value
        this.canNext()
    },
    nameCChange(e){
        this.name = e.value
        this.canNext()
    },
    phoneChange(e){
        this.phone = e.value
        this.canNext()
    },
    locationCChange(e){
        this.location = e.value
        this.canNext()
    },
    onInit(){
        if (this.operation === "EDIT") {
            this.relation = this.item.relation
            this.name = this.item.name
            this.phone = this.item.phone
            this.location = this.item.location
        }
        this.canNext()
    },
    onShow(){
        let that = this
        storage.get({
            key: "userId",
            success: function (data) {
                that.userId = data
            },
            fail(data){
                console.error(data)
            }
        })
    },
    edit(){
        if (this.can) {
            updateContact(this.item.id, this.name, this.phone, this.location, this.userId, this.relation).then(data=>{
                if(JSON.parse(data.data).data.status === "200"){
                    router.back()
                }
                console.log(data)
            }).catch(error=>{
                console.log(error);
            })
        }
    },
    delete(){
        if (this.can) {
            removeContact(this.id, this.userId).then(data=>{
                if(JSON.parse(data.data).data.status === "200"){
                    router.back()
                }
                console.log(data)
            }).catch(error=>{
                console.log(error);
            })
        }
    },
    add(){
        if (this.can) {
            addContact(this.name, this.phone, this.location, this.userId, this.relation).then(data=>{
                if(JSON.parse(data.data).data.status === "200"){
                    router.back()
                }
                console.log(data)
            }).catch(error=>{
                console.log(error);
            })
        }
    }
}
