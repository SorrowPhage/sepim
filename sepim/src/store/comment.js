import axios from "axios";

export default {
    namespaced: true,
    actions: {
        getComments(context,value) {
            axios.get("http://localhost:8080/api/md/comment/get",{params:{folderId: value}}).then(res=>{
                context.commit("GET_COMMENTS",res.data.data)
            })
        },
    },
    mutations: {
        GET_COMMENTS(state,value) {
            state.comments = value;
        },
    },
    state: {
        comments : [],
    },
}