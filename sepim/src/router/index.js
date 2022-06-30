import VueRouter from 'vue-router'
import axios from "axios";
import store from "@/store";

// 解决控制台因为冗余导航的报错
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
   return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
    routes: [
        {
            path: '/',
            component: () => import('@/components/UserLog'),
            meta: {title: '登录'}
        },
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
                    component: () => import('@/components/MainContext'),
                    redirect: '/index.html/main/el',
                    children:[
                        {
                            name: 'bfag',
                            path: 'bfag',
                            component: () => import('@/components/main/BasicFormatAndGrammar'),
                            meta: {ver: true}
                        },
                        {
                            name: 'el',
                            path: 'el',
                            component: () => import('@/components/main/EntryLevel'),
                            meta: {ver: true}
                        },
                    ]
                },
                {
                    name: 'sl',
                    path: 'sl',
                    component: () => import('@/components/search/SearchList'),
                    meta:{ver: true},
                    redirect:'/index.html/sl/rc',
                    children:[
                        {
                            name:'rc',
                            path: 'rc',
                            component: () => import('@/components/search/content/RepositoryContent'),
                            meta: {ver: true}
                        },
                        {
                            name:'uc',
                            path: 'uc',
                            component: () => import('@/components/search/content/UserContent'),
                            meta: {ver: true}
                        },
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
                        }
                    ]
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

            ]
        },
        {
            name:'404',
            path:'404',
            componentL:()=>import("@/components/error/404NotFond"),
            meta:{title: "404",ver: true}
        }
    ]
})
router.beforeEach((to,from,next)=>{
    if (to.meta.ver) {
        let token = localStorage.getItem("token");
        if (token === null) {
            next("/");
        }
        axios.post("http://localhost:8080/api/ver/token", {token: token}).then(res => {
            if (res.data.flag === "user_ver_succeed") {
                store.commit("User/getUserInfo", res.data.data);
                next();
            } else {
                console.log("未登录");
                next("/");
            }
        });
    } else {
        next();
    }
})
router.afterEach((to)=>{
	document.title = to.meta.title || '系统'
})
export default router