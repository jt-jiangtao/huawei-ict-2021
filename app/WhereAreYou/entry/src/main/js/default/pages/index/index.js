export default {
    data() {
        return {
            tabbardata: [
                {
                    i: 0,
                    title: '首页',
                    color:'#18181a',
                    selectedColor:'#0087f9',
                    img: '/common/Icon/head.png',
                    selectedImg: '/common/Icon/head_selected.png',
                    show: true,
                },
                {
                    i: 1,
                    title: '文章',
                    color:'#18181a',
                    selectedColor:'#0087f9',
                    img: '/common/Icon/article.png',
                    selectedImg: '/common/Icon/article_selected.png',
                    show: false,
                },
                {
                    i: 2,
                    title: '我的',
                    color:'#18181a',
                    selectedColor:'#0087f9',
                    img: '/common/Icon/my.png',
                    selectedImg: '/common/Icon/my_selected.png',
                    show: false,
                }
            ]
        }
    },
    onInit() {
    },
    changeTabIndex(e) {
        for (let i = 0; i < this.tabbardata.length; i++) {
            let element = this.tabbardata[i];
            element.show = false;
            if (i === e.index) {
                element.show = true;
            }
        }
    }
}
