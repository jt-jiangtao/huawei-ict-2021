import fetch from '@system.fetch';
import {baseUrl} from './support.js';

export function login(code) {
    let url = baseUrl + "/login/log?code=" + code
    return new Promise((resolve, reject) => {
        fetch.fetch({
            method:"POST",
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

export function verify(token) {
    let url = baseUrl + "/login/verify"
    return new Promise((resolve, reject) => {
        fetch.fetch({
            method: "POST",
            url,
            data: "token=" + token,
            success: function (data) {
                resolve(data)
            },
            fail: function (error) {
                reject(error)
            }
        })
    })
}
