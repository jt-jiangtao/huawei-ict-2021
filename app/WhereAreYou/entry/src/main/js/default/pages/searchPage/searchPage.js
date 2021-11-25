import router from '@system.router';
import {searchByKeyword} from '../../fetch/lossInfo.js'


export default {
    data: {
        searchInfo: [],
        searchValue: ""
    },
    back(){
        router.back()
    },
    search(){
        let that = this
        searchByKeyword(this.searchValue).then(data=>{
            that.searchInfo = JSON.parse(data.data).data.items
        })
    },
    change(e){
        this.searchValue = e.value
    },
    redirectToDetail(param) {
        console.info(param)
        router.push({
            uri: 'pages/infoDetail/infoDetail',
            params: {
                id: param
            }
        });
    },
}
