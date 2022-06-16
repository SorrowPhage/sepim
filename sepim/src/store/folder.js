import axios from "axios";

export default {
    namespaced: true,
    actions: {
        getMdList(context,value) {
            axios.post("http://localhost:8080/api/md/list",{userId: value}).then(res=>{
                context.commit("GET_MD_LIST",res.data.data)
            },error=>{
                console.log(error.message);
            })
        },
    },
    mutations: {
        GET_MD_LIST(state,data) {
            state.list = data;
        },
    },
    state: {
        id: '',
        user_id: '',
        username: '',
        title: "",
        roughly: "",
        type: "",
        state: "",
        list: [],
    },
}