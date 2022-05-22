export default {
    namespaced: true,
    actions: {},
    mutations: {
        changeCollapse(state) {
            state.isCollapse = !state.isCollapse
        }
    },
    state: {
        isCollapse: false
    },
}