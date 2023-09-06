import request from '@/utils/request'

/**
 * 发生验证码
 * @param data
 * @returns {*}
 */
export function sendCode(data) {
    return request({
        url: '/sendcode',
        method: 'post',
        data
    })
}

/**
 * 用户注册
 * @param data
 * @returns {*}
 */
export function enrol(data) {
    return request({
        url:'/enrol',
        method: 'post',
        data
    })
}

/**
 * 用户登录
 * @param data
 * @returns {*}
 */
export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data
    })
}

/**
 * 找回密码
 * @param data
 * @returns {*}
 */
export function retrieve(data) {
    return request({
        url: '/retrieve',
        method: 'post',
        data
    })
}

/**
 * 修改密码
 * @param data
 * @returns {*}
 */
export function change(data) {
    return request({
        url: '/change',
        method: 'post',
        data
    })
}