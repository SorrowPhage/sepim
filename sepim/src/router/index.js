import VueRouter from 'vue-router'
import axios from "axios";
import store from "@/store";

// 解决控制台因为冗余导航的报错
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
    // base:'phage',
    routes: [
        {
            path: '/login',
            name: 'login',
            component: () => import('@/components/UserLog'),
            meta: {title: '登录'}
        },
        {
            path: '/register',
            component: () => import('@/components/UserRegister'),
            meta: {title: '注册'}
        },
        {
            path: '/retrieve',
            component: () => import('@/components/RetrievePassword'),
            meta: {title: '找回密码'}
        },
        {
            path: '/',
            component: () => import('@/pages/HomePage'),
            meta: {title: 'Home',ver:false},
            redirect: '/index.html/main',
        },
        {
            name: 'index',
            path: '/index.html',
            meta: {ver: true},
            component: () => import('@/pages/HomePage'),
            redirect: '/index.html/main',
            children: [
                {
                    path: 'main',
                    name: 'main',
                    meta: {ver: true},
                    // meta: {ver: false},
                    component: () => import('@/components/MainContext'),
                    // redirect: '/index.html/main/el',
                    // children: [
                    //     {
                    //         name: 'bfag',
                    //         path: 'bfag',
                    //         component: () => import('@/components/main/BasicFormatAndGrammar'),
                    //         // meta: {ver: true}
                    //         meta: {ver: false}
                    //     },
                    //     {
                    //         name: 'el',
                    //         path: 'el',
                    //         component: () => import('@/components/main/EntryLevel'),
                    //         meta: {ver: false}
                    //     },
                    // ]
                },
                {
                    name: 'sl',
                    path: 'sl',
                    component: () => import('@/components/search/SearchList'),
                    meta: {ver: true},
                    // meta: {ver: false},
                    redirect: '/index.html/sl/rc',
                    children: [
                        {
                            name: 'rc',
                            path: 'rc',
                            component: () => import('@/components/search/content/RepositoryContent'),
                            meta: {ver: true}
                            // meta: {ver: false}
                        },
                        {
                            name: 'uc',
                            path: 'uc',
                            component: () => import('@/components/search/content/UserContent'),
                            meta: {ver: true}
                            // meta: {ver: false}
                        }
                    ]
                },
                //秒杀案例
                {
                    name: 'seckill',
                    path: 'seckill',
                    component: () => import("@/components/seckill/SeckillContent"),
                    meta: {title: "Redis", ver: true},
                    // meta: {title: "Redis",ver:false},
                },
                {
                    name: 'birthday',
                    path: 'birthday',
                    component: () => import("@/components/expression/HappyBirthday"),
                    meta: {title: "生日快乐", ver: true},
                },
                {
                    name: 'video',
                    path: 'video',
                    component: () => import("@/components/video/VideoPlayer"),
                    meta: {title: "Aurora", ver: true},
                    // meta: {title: "Aurora",ver:false},
                },
                {
                    name: 'aurora',
                    path: 'aurora',
                    component: () => import("@/components/video/player/PhageAurora"),
                    meta: {title: 'Aurora', ver: true}
                },
                {
                    name: 'music',
                    path: 'music',
                    component: () => import("@/components/music/MusicPlayer"),
                    meta: {title: "PhageVoice", ver: true},
                    redirect: '/index.html/music/musicmain',
                    children: [
                        {
                            name: 'musicmain',
                            path: 'musicmain',
                            component: () => import("@/components/music/main/MusicMain"),
                            meta: {title: "PhageVoice", ver: true},
                        },
                        {
                            name: 'musiclist',
                            path: 'musiclist',
                            component: () => import("@/components/music/music_list/MusicList"),
                            meta: {title: "PhageVoice", ver: true},
                            // children:[
                            //     {
                            //         name: 'develop',
                            //         path: 'develop',
                            //         component: () => import("@/components/music/music_list/develop/DevelopMusicList"),
                            //         meta: {title: "PhageVoice", ver: true},
                            //     },
                            // ]
                        },
                        {
                            name: 'develop',
                            path: 'develop',
                            component: () => import("@/components/music/music_list/develop/DevelopMusicList"),
                            meta: {title: "PhageVoice", ver: true},
                        },
                        {
                            name: 'play',
                            path: 'paly',
                            component: () => import("@/components/music/player/SongPlayer"),
                            meta: {title: "PhageVoice", ver: true},
                        }
                    ]
                },
                {
                    name: 'detail',
                    path: 'detail',
                    component: () => import("@/components/userdetail/DetailNavMenu"),
                    meta: {title: "UserDetail", ver: true},
                    redirect: '/index.html/detail/overview',
                    children: [
                        {
                            name: 'overview',
                            path: 'overview',
                            component: () => import("@/components/userdetail/details/UserOverview"),
                            meta: {title: "Overview", ver: true},
                        },
                        {
                            name: 'files',
                            path: 'files',
                            component: () => import("@/components/userdetail/details/FolderList"),
                            meta: {title: "UserDetail", ver: true},
                        },
                        {
                            name: 'insights',
                            path: 'insights',
                            component: () => import("@/components/userdetail/insights/Insights"),
                            meta: {title: "UserDetail", ver: true}
                        },
                        {
                            name: 'contribution',
                            path: 'contribution',
                            component: () => import("@/components/userdetail/contribution/Contribution"),
                            meta: {title: "UserDetail", ver: true}
                        },
                        // {
                        //     name: 'follow',
                        //     path: 'follow',
                        //     component: () => import("@/components/userdetail/follow/UserFollow"),
                        //     meta: {title: "UserDetail", ver: true},
                        //     redirect: '/index.html/detail/follow/phages',
                        //     children:[
                        //         {
                        //             name: 'phages',
                        //             path: 'phages',
                        //             component: () => import("@/components/userdetail/follow/PhageFollow"),
                        //             meta: {title: "UserDetail", ver: true}
                        //         },
                        //         {
                        //             name: 'erc',
                        //             path: 'erc',
                        //             component: () => import("@/components/userdetail/follow/ERCFollow"),
                        //             meta: {title: "UserDetail", ver: true}
                        //         },
                        //     ]
                        // },
                    ]
                },
                {
                    name: 'phages',
                    path: 'phages',
                    component: () => import("@/components/userdetail/follow/PhageFollow"),
                    meta: {title: "UserDetail", ver: true}
                },
                {
                    name: 'erc',
                    path: 'erc',
                    component: () => import("@/components/userdetail/follow/ERCFollow"),
                    meta: {title: "UserDetail", ver: true}
                },
                {
                    name: 'chat',
                    path: 'chat/:account/:username',
                    component: () => import("@/components/chat/PhageChat"),
                    meta: {title: "PhageChat", ver: true},
                },
                {
                    name: 'games',
                    path: 'games:/account',
                    component: () => import("@/components/games/PhageGames"),
                    meta: {title: "游戏", ver: true},
                },
                {
                    name: 'wuziqi',
                    path: "wuqizi/:account",
                    component: () => import("@/components/games/FiveInRow"),
                    meta: {title: "五子棋", ver: true},
                },
                {
                    name: 'overvieweditor',
                    path: 'overvieweditor',
                    component: () => import("@/components/userdetail/mavonEditor/OverviewEditor"),
                    meta: {title: "Overview", ver: true},
                },
                {
                    name: 'userinfo',
                    path: 'userinfo',
                    component: () => import('@/components/userinfo/CheckUserInfo'),
                    meta: {title: '个人信息', ver: true},
                },
                {
                    name: 'setting',
                    path: 'setting',
                    component: () => import('@/components/userinfo/SetUserInfo'),
                    meta: {title: '修改信息', ver: true},
                },
                {
                    name: 'setpassword',
                    path: 'setpassword',
                    component: () => import('@/components/userinfo/SetPassword'),
                    meta: {title: '修改密码', ver: true},
                },
                {
                    name: 'auditlist',
                    path: 'auditlist',
                    component: () => import('@/components/operation/examination/AuditList'),
                    meta: {title: '审核', ver: true},
                },
                {
                    name: 'leave',
                    path: 'leave',
                    component: () => import('@/components/operation/leave/AskOfLeaveList'),
                    meta: {title: '请假', ver: true},
                },
                {
                    name: 'doc_dep',
                    path: 'DD',
                    component: () => import("@/components/depository/DocDepository"),
                    meta: {title: "Repository", ver: true},
                },
                {
                    name: 'edit',
                    path: 'edit',
                    component: () => import("@/components/depository/EditProject"),
                    meta: {title: "Edit", ver: true},
                },
                {
                    name: 'newdoc',
                    path: 'ND',
                    component: () => import("@/components/depository/NewProject"),
                    meta: {title: "Repository", ver: true},
                },
                {
                    name: 'read',
                    path: 'read',
                    component: () => import("@/components/depository/list/ReadMd"),
                    meta: {title: "Repository", ver: true},
                },
                {
                    name: 'analysis',
                    path: 'analysis',
                    component: () => import("@/components/analysis/UserDataAnalysis"),
                    meta: {title: "Analysis", ver: true},
                    redirect: "/index.html/analysis/analysis",
                    children: [
                        {
                            name: 'analysis_index',
                            path: 'analysis',
                            component: () => import("@/components/analysis/vcharts/UserOverviewData"),
                            meta: {title: "Analysis", ver: true},
                        },
                        {
                            name: 'analysis_line',
                            path: 'line',
                            component: () => import("@/components/analysis/vcharts/FolderCreateLine"),
                            meta: {title: "Analysis", ver: true},
                        },
                        {
                            name: 'rediction',
                            path: 'predict',
                            component: () => import("@/components/analysis/prediction/PredictionModel"),
                            meta: {title: "Analysis", ver: true},
                        },
                        {
                            name: 'survey_csv',
                            path: 'csv',
                            component: () => import("@/components/analysis/prediction/survey/SurveyCSV"),
                            meta: {title: "Analysis", ver: true},
                        }
                    ]
                },
                {
                    name: 'czph',
                    path: 'czph',
                    component: () => import("@/components/czp/CzpHome"),
                    meta: {title: "cHome", ver: true},
                },
                {
                    name: 'czpt',
                    path: 'czpt',
                    component: () => import("@/components/czp/CzpTree"),
                    meta: {title: "cTree", ver: true},
                },
                {
                    name: 'add_czp',
                    path: 'add_czp',
                    component: () => import("@/components/czp/AddCzp"),
                    meta: {title: "add_czp", ver: true},
                }
            ]
        },

        {
            name: '404',
            path: '404',
            componentL: () => import("@/components/error/404NotFond"),
            meta: {title: "404", ver: true}
        }
    ]
})

router.beforeEach((to, from, next) => {
    if (!to.meta.ver){
        let token = localStorage.getItem("token");
        if (token === null) {
            next();
        } else {
            axios.post("http://localhost:8080/api/ver/token", {token: token}).then(res => {
                //验证成功
                if (res.data.flag === "user_ver_succeed") {
                    store.commit("User/getUserInfo", res.data.data);
                    next();
                } else {
                    //验证失败
                    console.log("未登录");
                    localStorage.removeItem("token")
                    next();
                }
            });
        }

    }
    if (to.meta.ver) {
        let token = localStorage.getItem("token");
        if (token === null) {
            next("/login");
        }
        axios.post("http://localhost:8080/api/ver/token", {token: token}).then(res => {
            if (res.data.flag === "user_ver_succeed") {
                store.commit("User/getUserInfo", res.data.data);
                next();
            } else {
                console.log("未登录");
                next("/login");
            }
        });
    } else {
        next();
    }
})
router.afterEach((to) => {
    document.title = to.meta.title || '系统'
})
export default router