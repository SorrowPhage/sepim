export default {
    namespaced: true,
    actions: {},
    mutations: {
        changeCollapse(state) {
            state.isCollapse = !state.isCollapse
        },
        getUserInfo(state,user){
            state.account = user.id;
            state.avatarUrl = user.avatarUrl;
            state.userName = user.username;
            state.email = user.email;
            state.type = user.type;
        },
        changeAvatar(state,url){
            state.avatarUrl = url;
        }
    },
    state: {
        account: 'Admin',
        'avatarUrl': '',
        'userName': 'Admin',
        email: '1@qq.com',
        isCollapse: true,
        type: '',
    },
    getters: {
        avatar_url(state) {
            if (state.avatarUrl === ''||state.avatarUrl === null) return "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            else return state.avatarUrl
        }
    }
}