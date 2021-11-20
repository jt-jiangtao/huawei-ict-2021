import fetch from '@system.fetch';
import {baseUrl} from './support.js';

export function getSwiperInfo() {
    let url = baseUrl + "/articles/get"
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