import fetch from '@system.fetch';
import {baseUrl} from './support.js';

export function getUserNotification(user) {
    let url = baseUrl + "/clew/get?userId=" + user + "&time=" + new Date().getUTCMilliseconds()
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

export function seen(id) {
    let url = baseUrl + "/clew/seen"
    return new Promise((resolve, reject) => {
        fetch.fetch({
            method: "POST",
            url,
            data: "id=" + id,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}