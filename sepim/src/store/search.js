import axios from "axios";

export default {
    namespaced: true,
    actions: {

    },
    mutations: {
        SET_SEARCH_CONTENT(state,value) {
            state.searchContent = value;
        },
    },
    state: {
        searchContent: '',
    },
}