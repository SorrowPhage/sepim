<template>
    <div class="left-menu left-side">
        <div class="logo">
            <img src="../assets/img/main/menu/logo.png" alt="加载失败"/>
        </div>
        <el-menu class="el-menu-vertical-demo" default-active="1-4-1" >
            <el-menu-item index="0" @click="goIndex">
                <i class="el-icon-menu"></i>
                <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu index="1">
                <template slot="title">
                    <i class="el-icon-user-solid"></i>
                    <span slot="title">个人中心</span>
                </template>
                <el-menu-item-group class="group">
                    <el-menu-item index="1-1" @click="checkUserInfo"
                    >个人信息
                    </el-menu-item
                    >
                    <el-menu-item index="1-3" @click="setPassword">修改密码</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
<!--            <el-menu-item v-if="type==='admin' " index="2" @click="audit">-->
<!--                <i class="el-icon-menu"></i>-->
<!--                <span slot="title">审核</span>-->
<!--            </el-menu-item>-->
<!--            <el-menu-item v-if="type==='user' " index="3" @click="leave">-->
<!--                <i class="el-icon-menu"></i>-->
<!--                <span slot="title">请假</span>-->
<!--            </el-menu-item>-->
            <el-menu-item index="5" @click="depository">
                <i class="el-icon-menu"></i>
                <span slot="title">Depository</span>
            </el-menu-item>
            <el-menu-item index="7" @click="pred">
                <i class="el-icon-menu"></i>
                <span slot="title">Pred</span>
            </el-menu-item>
            <el-menu-item index="6" @click="logOut">
                <i class="el-icon-right"></i>
                <span slot="title">退出</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
import {mapState} from "vuex";
import {sessionReplaceStore} from "@/utils/session_util"
export default {
    name: "NavMenu",
    computed: {
        ...mapState("Menu", ["isCollapse"]),
        ...mapState("User", ["type"]),
    },
    methods: {
        goIndex() {
            this.isTrue = false;
            this.$router.push({
                name: 'index'
            })
        },
        checkUserInfo() {
            this.$router.push({
                name: "userinfo",
            });
        },
        setPassword() {
            this.$router.push({
                name: "setpassword",
            });
        },
        audit() {
            this.$store.dispatch("Holiday/getAuditList");
            this.$router.push({
                name: 'auditlist',
            })
        },
        leave() {
            this.$store.dispatch("Holiday/getLeaveList", this.$store.state.User.account);
            this.$router.push({
                name: 'leave',
            })
        },
        depository() {
            this.$router.push({
                name: 'doc_dep',
            })
        },
        pred() {
        
        },
        logOut() {
            localStorage.removeItem("token");
            this.$router.push({
                name: "login"
            });
        }
    },
    created() {
        sessionReplaceStore('store');
        // 发生页面销毁事件前将store数据存储至sessionStoreage中
        window.addEventListener('beforeunload', () => {
            sessionStorage.setItem('store', JSON.stringify(this.$store.state))
        });
    },
};
</script>

<style scoped>
.left-menu {
    position: fixed;
    height: 100%;
    z-index: 100;
    overflow: hidden;
    outline: none;
    background-color: rgb(66, 79, 99);
}

.left-side {
    width: 240px;
    top: 0;
    left: 0;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 241px;
    height: 100%;
    background-color: rgb(66, 79, 99);
}

span {
    color: white;
}

.logo {
    padding-top: 5px;
    padding-left: 20px;
    height: 50px;
    background-color: rgb(66, 79, 99);
}

.group {
    background-color: white;
}

</style>

