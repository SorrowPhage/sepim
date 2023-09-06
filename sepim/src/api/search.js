import request from '@/utils/request'

/**
 * 搜索用户
 * @param data
 * @returns {*}
 */
export function searchUser(data) {
    return request({
        url: '/search/user',
        method: 'post',
        data
    })
}

/**
 * 搜索文章
 * @param query
 * @returns {*}
 */
export function searchRep(query) {
    return request({
        url: '/search/user',
        method: 'post',
        params:query
    })
}

