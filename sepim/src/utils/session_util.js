// 先建立seesion工具来统一调用
// util_session.js
import store from '../store'
// 用于sessionStorage替换store函数。
export function sessionReplaceStore (name) {
    return new Promise((resolve) => {
        if (sessionStorage.getItem(name)) {
            // 替换state状态
            store.replaceState(
                Object.assign(
                    {},
                    store.state,
                    JSON.parse(sessionStorage.getItem(name))
                )
            );
            sessionStorage.removeItem(name);
            resolve(true)
        } else {
            resolve(false)
        }
    })
}