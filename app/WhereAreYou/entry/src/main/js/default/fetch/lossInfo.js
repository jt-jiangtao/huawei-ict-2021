import fetch from '@system.fetch';
import {baseUrl} from './support.js';

export function getSimpleInfo() {
    let url = baseUrl + "/loss/simple_info" + "?time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function getLossDetailInfo(id){
    let url = baseUrl + "/loss/detail/" + id + "?time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function getLossSimpleInfoByUser(id){
    let url = baseUrl + "/loss/user/" + id + "?time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function searchByKeyword(key){
    let url = baseUrl + "/loss/key?key=" + key + "&time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function commitInfo(age, lossTime, lossLocation, reportPolice, name, sex, detailCharacters, caseDetail, userId, images, contacts, type){
    let url = baseUrl + "/loss/commit/" + (type === "CHILD" ? 'child' : 'parent')
    let data = "age=" + age + "&lossTime=" + lossTime + "&lossLocation=" + lossLocation + "&reportPolice="
                + reportPolice + "&name=" + name + "&sex=" + sex + "&detailCharacters=" + detailCharacters
                + "&caseDetail=" + caseDetail + "&userId=" + userId + "&images=" + JSON.stringify(images)
                + "&contacts=[" + JSON.stringify(contacts) + "]"
    return new Promise((resolve, reject) => {
        fetch.fetch({
            method: "POST",
            url,
            data,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function isCollect(id, event){
    let url = baseUrl + "/loss/is_collect?id=" + id + "&event=" + event + "&time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function collectAdd(id, event){
    let url = baseUrl + "/loss/collect/add?id=" + id + "&event=" + event + "&time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function collectRemove(id, event){
    let url = baseUrl + "/loss/collect/remove?id=" + id + "&event=" + event + "&time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function getSimpleInfoNumber(id){
    let url = baseUrl + "/loss/user?id=" + id + "&time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function collectNumber(id){
    let url = baseUrl + "/loss/collect/number?id=" + id + "&time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function getLossCollectByUser(id){
    let url = baseUrl + "/loss/collect/simple_info?id=" + id + "&time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function getLossChildByUser(user){
    let url = baseUrl + "/loss/get/child?user=" + user + "&time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}

export function getLossParentByUser(user){
    let url = baseUrl + "/loss/get/parent?user=" + user + "&time=" + new Date().getUTCMilliseconds()
    return new Promise((resolve, reject) => {
        fetch.fetch({
            url,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}