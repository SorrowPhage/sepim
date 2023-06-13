import axios from "axios";

export default {
    namespaced: true,
    actions: {
        getChatMessageList(context,value){
            axios.get("http://localhost:8080/api/chat/get", {params: {s1: value.s1, s2: value.s2}}).then(res => {
                context.commit("GET_CHAT_MESSAGE_LIST", res.data.data);
                console.log(2)
                // console.log(res.data.data)
            },Error=>{
                console.log(Error.message)
            });
        }
    },
    mutations: {
        GET_CHAT_MESSAGE_LIST(state,value){
            state.messages = value;
        }
    },
    state: {
        messages : [],
    },
}