<template>
    <div class="main">
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <header class="table-heading">
                            用户中心
                        </header>
                        <div class="pane-body">
                            <div class="sp-user-body"
                                 v-loading="loading"
                                 element-loading-text="拼命加载中"
                                 element-loading-spinner="el-icon-loading"
                                 element-loading-background="rgba(0, 0, 0, 0.8)">
                                <div class="sp-userinfo-box">
                                    <div class="sp-userinfo-avatar-box">
                                        <el-avatar :src="avatarUrl" :size="100"></el-avatar>
                                    </div>
                                    <div class="sp-userinfo-info-box">
                                        <div class="sp-info-username">{{username}}</div>
<!--                                        <div>{{email}}</div>-->
<!--                                        <div>{{account}}</div>-->
                                    </div>
                                </div>
                                <div class="sp-repository-box">
                                    <div class="sp-center-content">
                                        <FolderList :files="files"></FolderList>
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
import FolderList from "@/components/userdetail/details/FolderList";
export default {
    name: "DetailNavMenu",
    components:{FolderList},
    data() {
        return {
            account: '',
            email: '',
            username: '',
            avatarUrl: '',
            // id: '',
            loading: true,
            files: [],
        };
    },
    mounted() {
        axios.post('http://localhost:8080/api/user/center/get', {id: this.$route.query.account}).then(res => {
            if (res.data.flag === "user_center_get_succeed") {
                this.account = res.data.data.id;
                this.email = res.data.data.email;
                this.username = res.data.data.username;
                this.avatarUrl = res.data.data.avatarUrl;
                this.loading = false;
            }
        }, Error => {
            console.log(Error.message)
        });
        axios.post("http://localhost:8080/api/user/center/files", {userId: this.$route.query.account}).then(res => {
            if (res.data.flag === "user_center_getPublicFolders_succeed") {
                this.files = res.data.data;
            }
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
}
.overspread{
    width: 100vh;
}
.sp-font-bold{
    font-weight: bold;
}
.sp-user-body {
    /*background-color: #0c9a9a;*/
    width: 100%;
    display: flex;
    justify-content: center;
}
.sp-userinfo-box {
    width: 30%;
    display: flex;
    justify-content: center;
}
.sp-userinfo-avatar-box{
    background-color: white;
    width: 40%;
    display: flex;
    justify-content: center;
}
.sp-userinfo-info-box{
    /*background-color: #227cf9;*/
    width: 60%;
}
.sp-repository-box{
    width: 70%;
    /*background-color: dimgrey;*/
    font-size: 10px;
    font-weight: bold;
    padding: 12px;
    border-left: 1px solid darkgray;
}
.sp-center-content{
    margin-top: 10px;
    width: 100%;
    background-color: white;
    padding: 12px;
}
.sp-info-username{
    font-weight: bold;
}
</style>