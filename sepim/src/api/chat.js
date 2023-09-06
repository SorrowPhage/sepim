import request from '@/utils/request'


/**
 * 找出两个用户之间的聊天信息
 * @param query
 * @returns {*}
 */
export function getChatMessageList(query) {
    return request({
        url: '/chat/get',
        method: 'get',
        params: query,
    })
}


/**
 * 获取用户和其他用户之间的最后一条信息
 * @param query
 * @returns {*}
 */
export function getChatList(query) {
    return request({
        url: '/chat/chatlist',
        method: 'get',
        params: query,
    })
}