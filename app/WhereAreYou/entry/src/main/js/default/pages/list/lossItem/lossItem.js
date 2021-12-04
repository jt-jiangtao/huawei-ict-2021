import storage from '@system.storage';
import router from '@system.router';

export default {
    data: {
//        items: []
    },
    props: ['items'],
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
