/**
 * ajax请求配置
 */
import axios from 'axios'
import { Message } from 'element-ui'

// axios默认配置
axios.defaults.timeout = 10000 // 超时时间
// axios.defaults.baseURL 请求地址前缀
// User地址
// axios.defaults.baseURL = 'http://127.0.0.1:8001'
// tools地址
// axios.defaults.baseURL = 'http://127.0.0.1:8088'
// 微服务地址
axios.defaults.baseURL = 'http://localhost:8085'

// 整理数据
axios.defaults.transformRequest = function(data) {
    data = JSON.stringify(data)
    return data
}

// 路由请求拦截
axios.interceptors.request.use(
    config => {
        config.headers['Content-Type'] = 'application/json;charset=UTF-8'

        return config
    },
    error => {
        return Promise.reject(error.response)
    })

// 路由响应拦截
axios.interceptors.response.use(
    response => {
        if (response.data.success === false) {
            return Message.error(response.data.errDesc)
        } else {
            return response.data
        }
    },
    error => {
        return Promise.reject(error.response) // 返回接口返回的错误信息
    })
export default axios


// //request.js
// import axios from 'axios'; // 引入axios
// import qs from 'qs'; //引入qs模块，用来序列化post类型的数据，后面会提到
// // 先导入vuex,因为我们要使用到里面的状态对象
// // vuex的路径根据自己的路径去写
// import store from '@/store/index';
// // 路由文件，下面toLgin方法需要用到
// import router from '@/router/index';
// //导入封装cookie文件
// import { removeCookies } from "../utils/auth";
//
// // 环境的切换
// // 项目环境可能有开发环境、测试环境和生产环境。我们通过node的环境变量来匹配我们的默认的接口url前缀。axios.defaults.baseURL可以设置axios的默认请求地址
// if (process.env.NODE_ENV == 'development') {
// 	axios.defaults.baseURL = 'https://www.baidu.com';}
// else if (process.env.NODE_ENV == 'debug') {
// 	axios.defaults.baseURL = 'https://www.ceshi.com';
// }
// else if (process.env.NODE_ENV == 'production') {
// 	axios.defaults.baseURL = 'https://www.production.com';
// }
//
// // create an axios instance
// const service = axios.create({
//     baseURL: BASE_URL,
//     timeout: 30000,// request timeout
//     // "Content-Type": "application/x-www-form-urlencoded"
// })
//
// // post请求的时候，我们需要加上一个请求头，所以可以在这里进行一个默认的设置，
// // 即设置post的请求头为application/x-www-form-urlencoded;charset=UTF-8
// // axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
//
//
// /**
//  * 跳转登录页
//  * 携带当前页面路由，以期在登录页面完成登录后返回当前页面
//  */
// const toLogin = () => {
//     router.replace({
//         path: '/login',
//         query: {
//             redirect: router.currentRoute.fullPath
//         }
//     });
// }
// /**
//  * 请求失败后的错误统一处理
//  * @param {Number} status 请求失败的状态码
//  */
// const errorHandle = (status, other) => {
//     // 状态码判断
//     switch (status) {
//         // 401: 未登录状态，跳转登录页
//         case 401:
//             toLogin();
//             break;
//         // 403 token过期
//         // 清除token并跳转登录页
//         case 666:
//             alert('登录过期，请重新登录');
//             removeCookies('token');
//             removeCookies('user');
//             setTimeout(() => {
//                 toLogin();
//             }, 1000);
//             break;
//         // 404请求不存在
//         case 404:
//             alert('请求的资源不存在');
//             break;
//         case status:
//             alert(other);
//             break;
//         default:
//             console.log(other);
//     }
// }
//
// // 添加请求拦截器
// service.interceptors.request.use(config => {
//     console.log(config);
//     // 每次发送请求之前判断vuex中是否存在token
//     // 如果存在，则统一在http请求的header都加上token，这样后台根据token判断你的登录情况
//     // 即使本地存在token，也有可能token是过期的，所以在响应拦截器中要对返回状态进行判断
//     const token = store.state.token;
//     token && (config.headers.Authorization = token);
//     if(config.method=='post'){
//         config.data = qs.stringify(config.data)
//     }
//     return config;
// }, error => {
//     console.log(error);
//     Promise.error(error);
// })
//
// // 添加响应拦截器
// service.interceptors.response.use(
//     response => {
//         console.log(response);
//
//         // 如果返回的状态码代表错误
//         if (response.data.errno != 0) {
//             // errorHandle(response.data.errno, response.data.errmsg);
//             return Promise.reject(response.data);
//         } else {
//             return Promise.resolve(response.data);
//         }
//     },
//     error => {
//         console.log('error');
//         if (error.response.status) {
//             if (error.response.status != 200) {
//                 errorHandle(error.response.status, error.response.statusText);
//                 return Promise.reject(response);
//             } else {
//                 // 处理断网的情况
//                 // eg:请求超时或断网时，更新state的network状态
//                 // network状态在app.vue中控制着一个全局的断网提示组件的显示隐藏
//                 // 关于断网组件中的刷新重新获取数据，会在断网组件中说明
//                 // store.commit('changeNetwork', false);
//             }
//             return Promise.reject(error.response);
//         }
//     }
// )
// export default service;
