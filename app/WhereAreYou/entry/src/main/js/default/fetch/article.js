import fetch from '@system.fetch';
import {baseUrl} from './support.js';

export function getArticleSimpleInfo() {
    let url = baseUrl + "/articles/get_simple_info"
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
export function getArticleDetailInfo(id) {
    let url = baseUrl + "/articles/get/" + id
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