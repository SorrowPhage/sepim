import axios from "axios";

export default {
    namespaced: true,
    actions: {
        getMdList(context, value) {
            if (value.id === value.account) {
                axios.post("http://localhost:8080/api/md/list", {userId: value.account}).then(res => {
                    context.commit("GET_MD_LIST", res.data.data)
                }, error => {
                    console.log(error.message);
                });
            } else {
                axios.post("http://localhost:8080/api/user/center/files", {userId: value.id}).then(res => {
                    if (res.data.flag === "200") {
                        context.commit("GET_MD_LIST", res.data.data);
                    } else {
                        const data = [];
                        context.commit("GET_MD_LIST", data);
                    }
                });
            }

        },

    },
    mutations: {
        GET_DETAIL(state,val) {
            state.account = val.account;
            state.email = val.email;
            state.username = val.username;
            state.avatarUrl = val.avatarUrl;
            state.id = val.id;
            state.loading = false;
        },
        GET_MD_LIST(state, data) {
            state.files = data;
        },
    },
    state: {
        account: '',
        email: '',
        username:'',
        avatarUrl: '',
        loading: true,
        id: '',
        files: [],
    },

}