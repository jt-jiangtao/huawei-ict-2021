import {hmsjsb} from '@hmscore/hms-js-base'

export default {
    hmsData: {
        eventCallbackMap: {}
    },
    onCreate() {
        hmsjsb.init(this.hmsData.eventCallbackMap);
    },
    onDestroy() {
    }
}