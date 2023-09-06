import request from '@/utils/request'

/**
 * 严重token
 * @param data
 * @returns {*}
 */
export function verToken(data) {
    return request({
        url: '/ver/token',
        method: 'post',
        data
    })
}