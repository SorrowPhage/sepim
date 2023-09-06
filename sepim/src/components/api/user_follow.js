import request from "@/utils/request";


/**
 * 关注
 * @param data
 * @returns {*}
 */
export function follow(data) {
    return request({
        url: '/uf/follow',
        method: 'post',
        data
    })
}


/**
 * 取消关注
 * @param data
 * @returns {*}
 */
export function unfollow(data) {
    return request({
        url: '/uf/unfollow',
        method: 'post',
        data
    })
}

/**
 * 关注列表
 * @param data
 * @returns {*}
 */
export function followList(data) {
    return request({
        url: '/uf/list-follow',
        method: 'post',
        data
    })
}

/**
 * 粉丝列表
 * @param data
 * @returns {*}
 */
export function fansList(data) {
    return request({
        url: '/uf/list-fans',
        method: 'post',
        data
    })
}

/**
 * 粉丝数量
 * @param data
 * @returns {*}
 */
export function fansNums(data) {
    return request({
        url: '/uf/num-fans',
        method: 'post',
        data
    })
}

/**
 * 关注数量
 * @param data
 * @returns {*}
 */
export function followNums(data) {
    return request({
        url: '/uf/num-follow',
        method: 'post',
        data
    })
}

/**
 * 获取关注状态
 * @param data
 * @returns {*}
 */
export function getStatus(data) {
    return request({
        url: '/uf/status',
        method: 'post',
        data
    })
}