<template>
    <div class="main">
        <!--        <div class="page-heading">-->
        <!--            <h3>个人信息</h3>-->
        <!--            <el-breadcrumb separator="/">-->
        <!--                <el-breadcrumb-item>个人中心</el-breadcrumb-item>-->
        <!--                <el-breadcrumb-item>个人信息</el-breadcrumb-item>-->
        <!--            </el-breadcrumb>-->
        <!--        </div>-->
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <!--                        <header class="table-heading">详情</header>-->
                        <div class="pane-body">
                            <avatarCropper></avatarCropper>
                            <div class="userinfo_box">
                                <el-form ref="form" :model="userInfo" label-width="100px">
                                    <el-form-item label="账号：">
                                        <span class="sp-span">{{ userInfo.id }}</span>
                                    </el-form-item>
                                    <el-form-item label="用户名：">
                                        <el-input v-model="userInfo.username"></el-input>
                                    </el-form-item>
                                    <el-form-item label="邮箱：">
                                        <el-input v-model="userInfo.email"></el-input>
                                    </el-form-item>
                                    <!--                                    <el-form-item label="性别：">-->
                                    <!--                                        <el-input v-model="userInfo.sex"></el-input>-->
                                    <!--                                    </el-form-item>-->
                                    <el-form-item label="性别：">
                                        <el-select v-model="userInfo.sex" class="sp-space" placeholder="类型"
                                                   style="width: 100px">
                                            <el-option
                                                v-for="item in options"
                                                :key="item.value"
                                                :label="item.label"
                                                :value="item.value"
                                            >
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="生日：">
                                        <el-date-picker
                                            v-model="userInfo.birthday"
                                            placeholder="选择日期"
                                            type="date">
                                        </el-date-picker>
                                    </el-form-item>
                                    <el-form-item label="年龄：">
                                        <el-input v-model="age" :disabled="true"></el-input>
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
            options: [{
                value: '男',
                label: '男'
            }, {
                value: '女',
                label: '女'
            }],
            form: {},
            userInfo: {
                id: this.$store.state.User.account,
                username: this.$store.state.User.userName,
                email: this.$store.state.User.email,
                sex: this.$store.state.User.sex,
                birthday: new Date(this.$store.state.User.birthday),
            }
        }
    },
    computed: {
        ...mapState("User", ["userName", "account", "email"]),
        age() {
            return this.toAge(this.commonDate(this.userInfo.birthday));
        }
    },
    methods: {
        updateUserInfo() {
            this.userInfo.birthday = this.commonDate(this.userInfo.birthday);
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
        },
        commonDate(standardDate) {
            let date = new Date(standardDate)
            let y = date.getFullYear()
            let m = date.getMonth() + 1
            m = m < 10 ? ('0' + m) : m
            let d = date.getDate()
            d = d < 10 ? ('0' + d) : d
            let hh = date.getHours()
            hh = hh < 10 ? ('0' + hh) : hh;
            let mm = date.getMinutes()
            mm = mm < 10 ? ('0' + mm) : mm;
            let ss = date.getSeconds()
            ss = ss < 10 ? ('0' + ss) : ss;
            return y + '-' + m + '-' + d;
        },
        toAge(timeStamp) {
            let birthDate = this.toDate(timeStamp).substr(0, 4);
            let newDate = new Date().getFullYear();
            return (newDate - parseInt(birthDate));
        },
        toDate(number) {
            let n = number;
            let date = new Date(n);
            let Y = date.getFullYear() + '/';
            let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/';
            let D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            return (Y + M + D)
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
    /*background-color: rgb(228, 228, 228);*/
    height: 100%;
    /*overflow: auto;*/
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