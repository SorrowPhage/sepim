//该文件用于创建Vuex中最为核心的store
import Vue from 'vue'
//引入Vuex
import Vuex from 'vuex'

import Menu from './left-menu'
import User from './user'
import Holiday from "@/store/holiday";
import Folder from "@/store/folder";
import Comment from "@/store/comment";
import Detail from "@/store/detail";
import Search from "@/store/search";
import Music from "@/store/music";
//应用Vuex插件
Vue.use(Vuex)

export default new Vuex.Store({
    modules:{
        Menu,
        User,
        Holiday,
        Folder,
        Comment,
        Detail,
        Search,
        Music
    }
})