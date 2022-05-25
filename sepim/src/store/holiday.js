import axios from "axios";

export default {
    namespaced: true,
    actions: {
        getLeaveList(context, value) {
            axios.post('http://localhost:8080/api/leaves', {userId:value}).then(res => {
                context.commit("GET_LEAVE_LIST", res.data.data)
            }, error => {
                console.log(error.message)
            });
        },
        getAuditList(context) {
            axios.post('http://localhost:8080/api/audits', ).then(res => {
                context.commit("GET_AUDIT_LIST",res.data.data)
            },error=>{
                console.log(error.message)
            });
        },
    },
    mutations: {
        GET_LEAVE_LIST(state, value) {
            state.leaves = value;
        },
        GET_AUDIT_LIST(state, value) {
            state.audits = value;
        },
        CHANGE_STATE(state, value) {
            state.audits.filter((item)=>{
                if (item.id === value.id) {
                    item.state = value.state;
                    console.log(value.state)
                }
            })
        },
    },
    state: {
        leaves: [],
        audits: [],
    },
    getters: {

    }
}