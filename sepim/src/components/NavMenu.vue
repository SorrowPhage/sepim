<template>
    <div class="left-menu left-side">
        <div class="logo">
            <img src="../assets/img/main/menu/logo.png"/>
        </div>
        <el-menu default-active="1-4-1" class="el-menu-vertical-demo">
            <el-menu-item index="0" @click="goIndex">
                <i class="el-icon-menu"></i>
                <span slot="title" >首页</span>
            </el-menu-item>
            <el-submenu index="1">
                <template slot="title">
                    <i class="el-icon-location"></i>
                    <span slot="title">个人中心</span>
                </template>
                <el-menu-item-group class="group">
                    <el-menu-item index="1-1" @click="checkUserInfo"
                    >个人信息
                    </el-menu-item
                    >
                    <!--          <el-menu-item index="1-2" @click="setUserInfo">修改信息</el-menu-item>-->
                    <el-menu-item index="1-3" @click="setPassword">修改密码</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-menu-item index="2">
                <i class="el-icon-menu"></i>
                <span slot="title">导航二</span>
            </el-menu-item>
            <el-menu-item index="4">
                <i class="el-icon-setting"></i>
                <span slot="title">导航四</span>
            </el-menu-item>
            <el-menu-item index="5" @click="logOut">
                <i class="el-icon-right"></i>
                <span slot="title">退出</span>
            </el-menu-item>
        </el-menu>
    </div>
</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "NavMenu",
        methods: {
            goIndex() {
                this.isTrue = false;
                this.$router.push({
                    name:'main'
                })
            },
            checkUserInfo() {
                this.$router.push({
                    name: "userinfo",
                });
            },
            setUserInfo() {
                this.$router.push({
                    name: "setting",
                });
            },
            setPassword() {
                this.$router.push({
                    name: "setpassword",
                });
            },
            logOut(){
                this.$router.push({
                    name: "login"
                });
            }
        },
        computed: {
            ...mapState("Menu", ["isCollapse"]),
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

