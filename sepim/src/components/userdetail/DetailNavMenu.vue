<template>
    <div class="main">
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel"
                             v-loading="$store.state.Detail.loading"
                             element-loading-text="拼命加载中"
                             element-loading-spinner="el-icon-loading"
                             element-loading-background="rgba(0, 0, 0, 0.8)"
                    >
                        <div class="pane-body">
                            <div class="sp-user-body">
                                <div class="sp-userinfo-box" >
                                    <div class="sp-userinfo-avatar-box">
                                        <el-avatar :src="$store.state.Detail.avatarUrl" :size="100"></el-avatar>
                                    </div>
                                    <div class="sp-userinfo-info-box">
                                        <div class="sp-info-username">{{$store.state.Detail.username}}</div>
                                    </div>
                                </div>
                                <div class="sp-repository-box">
                                    <div>
                                        <el-menu
                                            :default-active="this.$route.path"
                                            class="el-menu-demo"
                                            mode="horizontal"
                                            @select="handleSelect"
                                            text-color="#000"
                                            :router="true"
                                            active-text-color="#398bff">
                                            <el-menu-item index="/index.html/detail/overview" :route="{name:'overview',query:{account: this.$store.state.Detail.id}}" class="el-icon-reading">Overview</el-menu-item>
                                            <el-menu-item index="/index.html/detail/files" class="el-icon-collection">Repositories</el-menu-item>
                                        </el-menu>
                                    </div>
                                    <div class="sp-center-content">
                                        <router-view></router-view>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import axios from "axios";
export default {
    name: "DetailNavMenu",
    inject:["reload"],
    data() {
        return {
            account: '',
            email: '',
            username: '',
            avatarUrl: '',
            loading: true,
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            // console.log(this.$route.path)
            // console.log(key, keyPath);
        },
        goOverview() {
            this.$router.push({
                name:'overview',
                query:{
                    account: this.$store.state.Detail.account,
                }
            })
        },
        goFolderList() {
            this.$router.push({
                name: 'files',
            })
        },
    },
    watch: {
        $route:{
            handler:function (val,oldVal){
                this.reload();
            },
            deep: true,
        }
    },
    created() {
        axios.post('http://localhost:8080/api/user/center/get', {id: this.$route.query.account}).then(res => {
            if (res.data.flag === "user_center_get_succeed") {
                this.$store.commit("Detail/GET_DETAIL",{
                    account:res.data.data.id,
                    email:res.data.data.email,
                    username:res.data.data.username,
                    avatarUrl:res.data.data.avatarUrl,
                    id:this.$route.query.account,
                    loading:false,
                })
            }
        }, Error => {
            console.log(Error.message)
        });
        
    }
}
</script>

<style scoped>
* {
    box-sizing: border-box;
}

.main {
    margin-top: 50px;
    /*background-color: rgb(228, 228, 228);*/
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
    padding: 5px;
}
.overspread{
    width: 100vh;
}
.sp-font-bold{
    font-weight: bold;
}
.sp-user-body {
    width: 100%;
    display: flex;
    justify-content: center;
}
.sp-userinfo-box {
    width: 25%;
    height: 250px;
    display: flex;
    align-items: center;
    flex-wrap: wrap;
}
.sp-userinfo-avatar-box{
    width: 100%;
    display: flex;
    justify-content: center;
}
.sp-userinfo-info-box{
    width: 100%;
    text-align: center;
}
.sp-repository-box{
    width: 75%;
    font-size: 10px;
    font-weight: bold;
    padding: 5px;
    /*border-left: 1px solid darkgray;*/
}
.sp-center-content{
    width: 100%;
    background-color: white;
    padding: 5px;
}
.sp-info-username{
    font-weight: bold;
}
</style>