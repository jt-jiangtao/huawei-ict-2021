import router from '@system.router';

export default {
    data: {
        title: 'World'
    },
    redirectToDetail() {
        router.push({
            uri: 'pages/articleDetail/articleDetail'
        });
    },
    back(){
        router.back();
    }
}
