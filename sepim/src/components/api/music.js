import request from '@/utils/request'

/**
 * 上传音乐
 * @param data
 * @returns {*}
 */
export function uploadMusic(data) {
    return request({
        url: '/music/upload',
        method: 'post',
        data
    })
}


export function getMusicList(data) {
    return request({
        url: '/music/list',
        method: 'post',
        data
    })
}


export function getUserMusicList(data) {
    return request({
        url: '/mlist/get',
        method: 'post',
        data
    })
}

export function updateMusicListInformation(data) {
    return request({
        url: '/list/update',
        method: 'post',
        data
    })
}

export function getMusicListByMusicListId(data) {
    return request({
        url: '/list/only',
        method: 'post',
        data
    })
}

export function getMusicLrc(data) {
    return request({
        url: '/music/lrc',
        method: 'post',
        data
    })
}

export function getMusicListName(data) {
    return request({
        url: '/list/name',
        method: 'post',
        data
    })
}