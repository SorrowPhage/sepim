import VueRouter from 'vue-router'

// 解决控制台因为冗余导航的报错
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
   return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
    routes:[
        {
            path:'/',
            component:()=>import('@/components/UserLog'),
            meta:{title:'登录'}
        },
        {
            path:'/login',
            name:'login',
            component:()=>import('@/components/UserLog'),
            meta:{title:'登录'}
            
        },
        {
            path:'/register',
            component:()=>import('@/components/UserRegister'),
            meta:{title:'注册'}
        },
        {
            path:'/retrieve',
            component:()=>import('@/components/RetrievePassword'),
            meta:{title:'找回密码'}
        },
        {
            path:'/index.html',
            component:()=>import('@/pages/HomePage'),
            children:[
                {
                    path:'main',
                    name: 'main',
                    component:()=>import('@/components/MainContext'),
                },
                {
                    name:'userinfo',
                    path:'userinfo',
                    component:()=>import('@/components/userinfo/CheckUserInfo'),
                    meta:{title:'个人信息'},
                },
                {
                    name:'setting',
                    path:'setting',
                    component:()=>import('@/components/userinfo/SetUserInfo'),
                    meta:{title:'修改信息'},
                },
                {
                    name:'setpassword',
                    path:'setpassword',
                    component:()=>import('@/components/userinfo/SetPassword'),
                    meta:{title:'修改密码'},
                },
            ]
        },
        
    ]
})

router.afterEach((to)=>{
	document.title = to.meta.title || '疫情管理系统'
})
export default router