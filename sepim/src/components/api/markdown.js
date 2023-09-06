import request from '@/utils/request'


/**
 * 文章上传
 * @param data
 * @returns {*}
 */
export function saveMd(data) {
    return request({
        url: '/chat/get',
        method: 'post',
        data,
    })
}

/**
 * 查询文章列表--分页
 * @param data
 * @returns {*}
 */
export function getMdListPage(data) {
    return request({
        url: '/md/list/page',
        method: 'post',
        data,
    })
}

/**
 * 修改文章
 * @param data
 * @returns {*}
 */
export function editMd(data) {
    return request({
        url: '/md/update',
        method: 'post',
        data,
    })
}

/**
 * 文章列表
 * @param data
 * @returns {*}
 */
export function getMdList(data) {
    return request({
        url: '/md/list',
        method: 'post',
        data,
    })
}

/**
 * 获取文章
 * @param query
 * @returns {*}
 */
export function readMd(query) {
    return request({
        url: '/md/read',
        method: 'get',
        params:query
    })
}

/**
 * 获取文章
 * @param query
 * @returns {*}
 */
export function readMdContent(query) {
    return request({
        url: '/md/readMd',
        method: 'get',
        params:query
    })
}


/**
 * 上传图片
 * @param data
 * @returns {*}
 */
export function uploadMdImg(data) {
    return request({
        url: '/md/upload',
        method: 'post',
        data
    })
}

/**
 * 删除图片
 * @param query
 * @returns {*}
 */
export function deleteImgUrl(query) {
    return request({
        url: '/md/delete',
        method: 'get',
        params:query
    })
}

/**
 * 删除文章
 * @param query
 * @returns {*}
 */
export function deleteMd(query) {
    return request({
        url: '/md/remove',
        method: 'get',
        params:query
    })
}

/**
 * 发布评论
 * @param data
 * @returns {*}
 */
export function release(data) {
    return request({
        url: '/md/comment/release',
        method: 'post',
        data
    })
}

/**
 * 获取评论列表
 * @param query
 * @returns {*}
 */
export function getComments(query) {
    return request({
        url: '/md/comment/get',
        method: 'get',
        params:query
    })
}

/**
 * 排行榜
 * @param query
 * @returns {*}
 */
export function getRankList(query) {
    return request({
        url: '/md/rank',
        method: 'get',
        params:query
    })
}

/**
 * 上传本地文章
 * @param data
 * @returns {*}
 */
export function storeMd(data) {
    return request({
        url: '/md/store',
        method: 'post',
        data
    })
}

/**
 * 点赞
 * @param data
 * @returns {*}
 */
export function likeMd(data) {
    return request({
        url: '/md/like',
        method: 'post',
        data
    })
}

/**
 * 取消点赞
 * @param data
 * @returns {*}
 */
export function unlikeMd(data) {
    return request({
        url: '/md/unlike',
        method: 'post',
        data
    })
}

/**
 * 获取点赞状态
 * @param data
 * @returns {*}
 */
export function getLikeStatus(data) {
    return request({
        url: '/md/like/status',
        method: 'post',
        data
    })
}


/**
 * 获取echarts数据
 * @param data
 * @returns {*}
 */
export function getEchartsData(data) {
    return request({
        url: '/md/echarts-data',
        method: 'post',
        data
    })
}