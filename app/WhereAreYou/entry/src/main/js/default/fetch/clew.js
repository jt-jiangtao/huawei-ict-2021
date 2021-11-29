import fetch from '@system.fetch';
import {baseUrl} from './support.js';

export function provideClew(fromUser, toUser, clew) {
    // -2表示匿名用户
    let from = (fromUser.startsWith("user@") ? fromUser.split("@")[1] : -2);
    let to_n = toUser + 10000000
    let to = "user@" + to_n
    let url = baseUrl + "/clew/send"
    return new Promise((resolve, reject) => {
        fetch.fetch({
            method: "POST",
            url,
            data: "fromUser=" + from + "&toUser=" + to + "&clew=" + clew,
            success: function(data){
                resolve(data)
            },
            fail: function(error){
                reject(error)
            }
        })
    })
}