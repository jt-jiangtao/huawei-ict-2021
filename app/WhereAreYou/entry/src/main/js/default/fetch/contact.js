import fetch from '@system.fetch';
import {baseUrl} from './support.js';

export function getContact(userId) {
    let url = baseUrl + "/loss/get/contact?id=" + userId
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