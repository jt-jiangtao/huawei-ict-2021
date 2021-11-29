import fetch from '@system.fetch';
import {baseUrl} from './support.js';

export function getContact(userId) {
    let url = baseUrl + "/loss/get/contact?id=" + userId + "&time=" + new Date().getUTCMilliseconds()
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

export function updateContact(id, name, phone, location, userId, relation) {
    let url = baseUrl + "/loss/update/contact"
    return new Promise((resolve, reject) => {
        fetch.fetch({
            method: "POST",
            data: "id=" + id + "&name=" + name + "&phone=" + phone + "&location=" + location + "&userId=" + userId + "&relation=" + relation,
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

export function addContact(name, phone, location, userId, relation) {
    let url = baseUrl + "/loss/add/contact"
    return new Promise((resolve, reject) => {
        fetch.fetch({
            method: "POST",
            data: "name=" + name + "&phone=" + phone + "&location=" + location + "&userId=" + userId + "&relation=" + relation,
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

export function removeContact(id, userId) {
    let url = baseUrl + "/loss/remove/contact"
    return new Promise((resolve, reject) => {
        fetch.fetch({
            method: "POST",
            data: "id=" + id + "&userId=" + userId,
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