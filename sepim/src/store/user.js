export default {
    namespaced: true,
    actions: {
    },
    mutations: {
        changeCollapse(state) {
            state.isCollapse = !state.isCollapse
        },
        getUserInfo(state,user){
            if (user.token !== null) {
                //防止不是路由跳转的请求返回更新用户信息时将token设为null
                localStorage.setItem("token", user.token);
            }
            state.account = user.id;
            state.avatarUrl = user.avatarUrl;
            state.userName = user.username;
            state.email = user.email;
            state.type = user.type;
            state.sex = user.sex;
            state.birthday = user.birthday;
            state.face_url = user.faceUrl;
            console.log(state.face_url)
        },
        changeAvatar(state,url){
            state.avatarUrl = url;
        },
        changeFaceUrl(state,url){
            state.face_url = url;
        },
    },
    state: {
        account: '',
        'avatarUrl': '',
        'userName': '',
        email: '',
        isCollapse: true,
        type: '',
        sex: '',
        birthday: '',
        'face_url': '',
    },
    getters: {
        avatar_url(state) {
            if (state.avatarUrl === ''||state.avatarUrl === null) return "http://localhost:8088/upload/sepim/avatar/default.png"
            else return state.avatarUrl
        }
    }
}