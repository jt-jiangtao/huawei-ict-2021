import FA from '../support/fa_request.js'

export default async function request(userId) {
    var actionData = {};
    actionData.userId = userId;

    var action = {};
    action.bundleName = 'com.cyj.whereareyou';
    action.abilityName = 'com.cyj.whereareyou.service.OpenUIService';
    action.messageCode = 1004;
    action.data = actionData;
    action.abilityType = FA.ABILITY_TYPE_EXTERNAL;
    action.syncOption = FA.ACTION_SYNC;

    var result = await FeatureAbility.callAbility(action);
    var ret = JSON.parse(result);
    if (ret.code == 0) {
        this.title = JSON.stringify(ret.abilityResult);
        console.info('plus result is:' + JSON.stringify(ret.abilityResult));
    } else {
        console.error('plus error code:' + JSON.stringify(ret.code));
    }
}