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
                                    <div class="sp-userinfo-primary-box">
                                        <div class="sp-userinfo-username-box">
                                            <div class="sp-userinfo-avatar-box">
                                                <el-avatar :src="$store.state.Detail.avatarUrl" :size="60"></el-avatar>
                                            </div>
                                            <div class="sp-userinfo-xxxx">
                                                <div class="sp-info-username">{{$store.state.Detail.username}}</div>
                                            </div>
                                        </div>
                                        <div v-show="$store.state.Detail.id!==$store.state.User.account"
                                             class="sp-userinfo-info-box">
                                            <el-button type="danger" @click="goChat()">私信</el-button>
<!--                                            <el-button type="danger" @click="goWUzQi">五子棋</el-button>-->
                                        </div>
                                        <div v-show="$store.state.Detail.id===$store.state.User.account">
                                            <el-input v-model="searchUser"></el-input>
                                        </div>
                                    </div>
                                    <div class="sp-user-line-box" v-show="$store.state.Detail.id===$store.state.User.account" v-for="chat in chatList" :key="chat.id">
                                        <chat-user-line :account="chat.user.id" :username="chat.user.username" :avatar-url="chat.user.avatarUrl" :last-message="chat.content" :time="formatDate(chat.sendTime)" :num="chat.noReadNum"></chat-user-line>
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
                                            <el-menu-item index="/index.html/detail/files" :route="{name:'files',query:{account: this.$store.state.Detail.id}}" class="el-icon-collection">Repositories</el-menu-item>
                                            <el-menu-item index="/index.html/detail/insights" :route="{name:'insights',query:{account: this.$store.state.Detail.id}}" class="el-icon-view">Insights</el-menu-item>
                                            <el-menu-item index="/index.html/detail/contribution" :route="{name:'contribution',query:{account: this.$store.state.Detail.id}}" class="el-icon-folder-add">Contribution</el-menu-item>
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
import ChatUserLine from "@/components/chat/ChatUserLine";

export default {
    name: "DetailNavMenu",
    inject: ["reload"],
    components:{
        ChatUserLine
    },
    data() {
        return {
            x: 'http://localhost:8088/upload/sepim/avatar/ba0640727d374101b6c041ece7a40b4afile.png',
            m: 'SE,SrpingMVC,SpringBoot,Vue,MySql，Mybatis等技术有所了解。\n' +
                '这个网站是前端页面用Vue写成的，后台用的是SpringBoot',
            u: 'Hello',
            time:'3-11',
            account: '',
            email: '',
            username: '',
            avatarUrl: '',
            loading: true,
            chatList: [],
            searchUser: '',
        };
    },
    // computed: {
    //     userlists() {
    //         return this.chatList.filter((user) => {
    //             return user.fromId.indexOf(this.searchUser) !== -1 || user.toId.indexOf(this.keyword)!== -1;
    //         });
    //     },
    // },
    methods: {
        formatDate(sendTime) {
            let a = new Date(sendTime).getTime() - 8 * 3600 * 1000;
            const date = new Date(a) ;
            const Y = date.getFullYear() + '/';
            const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/';
            const D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + '  ';
            const h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
            const m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
            // const s = date.getSeconds(); // 秒
            // console.log('dateString', dateString); // > dateString 2021-07-06 14:23
            return Y + M + D + h + m;
        },
        goWUzQi() {
            this.$router.push({
                name:'wuziqi',
                params:{
                    account: this.$store.state.Detail.account,
                }
            })
        },
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
        goChat() {
            this.$router.push({
                name: 'chat',
                params:{
                    account: this.$store.state.Detail.account,
                    username: this.$store.state.Detail.username,
                    url: this.$store.state.Detail.avatarUrl,
                }
            })
        },
        goFolderList() {
            this.$router.push({
                name: 'files',
            })
        },
        loadData() {
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
            axios.get('http://localhost:8080/api/chat/chatlist',{params:{user: this.$route.query.account}}).then(res=>{
                if (res.data.flag === "200") {
                    this.chatList = res.data.data;
                    console.log(this.chatList)
                }
            })
        },
    },
    watch: {
        '$route.query.account':{
            handler() {
                this.loadData()
            },
        }
    },
    mounted() {
        this.loadData()
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
    padding: 5px;
    min-width: 300px;
    /*height: 250px;*/
    /*display: flex;*/
    /*align-items: center;*/
    /*flex-wrap: wrap;*/
}
.sp-userinfo-primary-box{
    background-image: linear-gradient(
        125deg,
        #007fff,
        #00bfff
    );
}
.sp-userinfo-username-box{
    display: flex;
}
.sp-userinfo-avatar-box{
    width: 60px;
    /*display: flex;*/
    /*justify-content: center;*/
    margin-top: 20px;
    margin-bottom: 5px;
    margin-left: 5px;
    
}
.sp-userinfo-info-box{
    width: 100%;
    text-align: center;
    margin-top: 5px;
    margin-bottom: 5px;
}
.sp-userinfo-xxxx{
    margin-top: 20px;
    margin-left: 10px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
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
    color: white;
    font-family: ui-monospace,SFMono-Regular,SF Mono,Menlo,Consolas,Liberation Mono,monospace !important;
    
}
.sp-user-line-box{
    border-top: 1px solid dimgrey;
    width: 100%;
    /*height: 40%;*/
    margin-top: 5px;
    overflow: auto;
}

.el-input /deep/ .el-input__inner {
    background-color: #00bfff;
    border: 0;
    border-radius: 0;
    color: white;
}


</style>