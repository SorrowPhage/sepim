<template>
    <div class="main">
        <div class="page-heading">
            <h3>个人信息</h3>
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>个人中心</el-breadcrumb-item>
                <el-breadcrumb-item>个人信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <header class="table-heading">详情</header>
                        <div class="pane-body">
                            <avatarCropper></avatarCropper>
                            <div class="userinfo_box">
                                <el-form ref="form" :model="form" label-width="100px">
                                    <el-form-item label="账号：">
                                        <span class="sp-span">{{ userInfo.id }}</span>
                                    </el-form-item>
                                    <el-form-item label="用户名：">
                                        <el-input v-model="userInfo.username"></el-input>
                                    </el-form-item>
                                    <el-form-item label="邮箱：">
                                        <el-input v-model="userInfo.email"></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="updateUserInfo">保存</el-button>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapState} from 'vuex'
import axios from "axios"
import {sessionReplaceStore} from "@/utils/session_util"
import avatarCropper from "@/components/userinfo/avatar_cropper/avatarCropper";
export default {
    name: "CheckUserInfo",
    components: {
        avatarCropper,
    },
    data() {
        return {
            form: {},
            userInfo: {
                id: this.$store.state.User.account,
                username: this.$store.state.User.userName,
                email: this.$store.state.User.email,
            }
        }
    },
    computed: {
        ...mapState("User", ["userName", "account", "email"]),
    },
    methods: {
        updateUserInfo() {
            axios.post("http://localhost:8080/api/update", this.userInfo).then(res => {
                if (res.data.flag === "user_info_update_succeed") {
                    this.$store.commit("User/getUserInfo", res.data.data);
                    this.$message({
                        message: '修改成功',
                        showClose: true,
                        type: 'success',
                        center: true,
                    });
                }
            })
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
/*会影响登录页面的样式*/
* {
    box-sizing: border-box;
}

.main {
    margin-top: 50px;
    background-color: rgb(228, 228, 228);
    height: 100%;
    overflow: auto;
}

.page-heading {
    width: 100%;
    padding: 15px;
    position: relative;
}

.page-heading h3 {
    display: block;
    font-size: 1.17em;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0;
    margin-inline-end: 0;
    font-weight: bold;
}

.wrapper {
    padding: 15px;
}

.row {
    margin-left: -15px;
    margin-right: -15px;
}

.col {
    position: relative;
    min-height: 1px;
    padding-left: 15px;
    padding-right: 15px;
    width: 100%;
}

.panel {
    margin-bottom: 20px;
    background-color: white;
    border: 1px solid transparent;
    border-radius: 4px;
    /*-webkit-box-shadow: 0 1px 1px rgb(0 0 0 / 5%);*/
    /*box-shadow: 0 1px 1px rgb(0 0 0 / 5%);*/
}

.table-heading {
    border-bottom: 1px dotted rgba(0, 0, 0, 0.2);
    padding: 15px;
    text-transform: uppercase;
    color: #535351;
    font-size: 14px;
    font-weight: bold;
}

.pane-body {
    padding: 15px;
    display: flex;
    align-items: center;
    justify-content: center;
}

table {
    border-collapse: collapse;
    border-spacing: 0;
}

.table thead > tr > th,
.table tbody > tr > td {
    padding: 10px;
}

.table-bordered > thead > tr > th {
    border-bottom-width: 2px;
}

.table-bordered > thead > tr > th,
.table-bordered > tbody > tr > td {
    border: 1px solid #ddd;
}

.table-striped > tbody > tr:nth-child(odd) > td,
.table-striped > tbody > tr:nth-child(odd) > th {
    background-color: #f9f9f9e1;
}

th {
    text-align: left;
}

.sp-span {
    padding: 0 15px;
}
</style>