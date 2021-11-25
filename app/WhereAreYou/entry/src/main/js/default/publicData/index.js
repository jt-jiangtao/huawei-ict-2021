import storage from '@system.storage';

export function storageGet(key) {
    return new Promise((resolve, reject) => {
        storage.get({
            key,
            success: function (data) {
                console.error("get("+ key +")-->")
                console.error(data)
                resolve(data)
            }
        });
    })
}

export function storageSet(key, value) {
    return new Promise((resolve, reject) => {
        storage.set({
            key,
            value,
            complete: function () {
                console.error("complete("+ key +")-->")
                resolve()
            },
        })
    })
}

export function storageClear() {
    return new Promise((resolve, reject) => {
        storage.clear({
            success: function (data) {
                resolve(data)
            },
            complete: function (data) {
                console.error(data)
                reject()
            },
        })
    })
}

export function storageDelete(key) {
    return new Promise((resolve, reject) => {
        storage.delete({
            key,
            success: function (data) {
                resolve(data)
            },
            complete: function (data) {
                console.error("complete("+ key +")-->")
                console.error(data)
                reject()
            },
        })
    })
}
