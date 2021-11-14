import router from '@system.router';
//import prompt from '@system.prompt';

export default {
    data: {
    },
    redirectToDetail() {
        router.push({
            uri: 'pages/infoDetail/infoDetail'
        });
    },
    onTextClick() {
        this.$element("apiMenu").show({x: 365, y: 30});
    },
    onMenuSelected(e){
        switch(e.value){
            case "1":
                router.push({
                    uri: 'pages/publish/findChild/findChild'
                });
                break;
            case "2":
                router.push({
                    uri: 'pages/publish/findParent/findParent'
                });
                break;
            case "3":
                router.push({
                    uri: 'pages/publish/urgencyPublish/urgencyPublish'
                });
                break;
        }
    }
}
