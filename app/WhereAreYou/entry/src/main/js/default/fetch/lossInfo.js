import fetch from '@system.fetch';
import {baseUrl} from './support.js';

export function getSimpleInfo() {
    let url = baseUrl + "/loss/simple_info"
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
    let url = baseUrl + "/loss/detail/" + id
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
    let url = baseUrl + "/loss/user/" + id
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
    let url = baseUrl + "/loss/key?key=" + key
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