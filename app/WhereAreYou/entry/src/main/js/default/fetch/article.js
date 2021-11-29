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
export function getArticleLikeCollectInfo(article, user) {
    let url = baseUrl + "/articles/article/lc_info?article=" + article + "&user=" + user
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
export function addLike(article, user) {
    let url = baseUrl + "/articles/like/add?article=" + article + "&user=" + user + "&time=" + new Date().getUTCMilliseconds()
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
export function removeLike(article, user) {
    let url = baseUrl + "/articles/like/remove?article=" + article + "&user=" + user + "&time=" + new Date().getUTCMilliseconds()
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
export function addCollect(article, user) {
    let url = baseUrl + "/articles/collect/add?article=" + article + "&user=" + user + "&time=" + new Date().getUTCMilliseconds()
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
export function removeCollect(article, user) {
    let url = baseUrl + "/articles/collect/remove?article=" + article + "&user=" + user + "&time=" + new Date().getUTCMilliseconds()
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

export function getUserCollectInfo(user) {
    let url = baseUrl + "/articles/collect/user/info?user=" + user + "&time=" + new Date().getUTCMilliseconds()
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