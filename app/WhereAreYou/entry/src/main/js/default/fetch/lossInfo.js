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