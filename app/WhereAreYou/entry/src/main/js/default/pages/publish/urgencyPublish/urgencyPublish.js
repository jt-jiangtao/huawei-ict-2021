import router from '@system.router';
import prompt from '@system.prompt';

export default {
    data: {
        datetimevalue: "2021-10-23 10:45"
    },
    back(){
        router.back();
    },
    datetimeonchange(e) {
        this.datetimevalue=e.year+"-"+(e.month+1)+"-"+e.day+" "+e.hour+":"+e.minute;
    }
}
