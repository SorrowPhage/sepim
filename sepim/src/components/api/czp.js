import request from '@/utils/request'


/**
 * 找出某地区的人物关系图
 * @param data
 * @returns {*}
 */
export function getRelationChat(data) {
    return request({
        url: '/czp/rc',
        method: 'post',
        data
    })
}

export function getRelation2All(data){
    return request({
        url: "/czp/all_rc",
        method: 'post',
        data
    })
}

export function batchNoList(query) {
    return request({
        url: "/czp/batchnos",
        method: 'get',
        params: query
    })
}

