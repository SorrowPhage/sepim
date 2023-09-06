import request from '@/utils/request'


/**
 * 获取用户信息
 * @param data
 * @returns {*}
 */
export function getUser(data) {
    return request({
        url: '/user/center/get',
        method: 'post',
        data
    })
}

/**
 * 访问其他用户时获取该用户公开的文章列表
 * @param data
 * @returns {*}
 */
export function getPublicFolders(data) {
    return request({
        url: '/user/center/files',
        method: 'post',
        data
    })
}

/**
 * 修改用户描述文件
 * @param data
 * @returns {*}
 */
export function overview(data) {
    return request({
        url: '/md/overview',
        method: 'post',
        data
    })
}

/**
 * 获取readme文档
 * @param data
 * @returns {*}
 */
export function readme(data) {
    return request({
        url: '/md/readme',
        method: 'post',
        data
    })
}