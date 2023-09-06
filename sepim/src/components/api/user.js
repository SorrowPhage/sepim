import request from '@/utils/request'

/**
 * 修改头像
 * @param data
 * @returns {*}
 */
export function settingAvatar(data) {
    return request({
        url: '/avatar',
        method: 'post',
        data
    })
}


/**
 * 用户修改个人信息
 * @param data
 * @returns {*}
 */
export function userInfoUpdate(data) {
    return request({
        url: '/updater',
        method: 'post',
        data
    })
}

/**
 * 用户修改密码
 * @param data
 * @returns {*}
 */
export function userUpdatePassword(data) {
    return request({
        url: '/update/password',
        method: 'post',
        data
    })
}

/**
 * 用户上传人脸绑定账号
 * @param data
 * @returns {*}
 */
export function uploadFaceImage(data) {
    return request({
        url: '/user/face',
        method: 'post',
        data
    })
}

/**
 * 用户人脸识别登录
 * @param data
 * @returns {*}
 */
export function faceLogin(data) {
    return request({
        url: '/user/fare',
        method: 'post',
        data
    })
}