<template>
    <div class="sp-overview">
        <div class="sp-overview-userinfo-box">
            <div class="sp-overview-userinfo-username">{{$store.state.Detail.username}}/readme.md</div>
            <div v-if="$store.state.Detail.id===$store.state.User.account" class="sp-overview-userinfo-edit"><i class="el-icon-edit" @click="goEditor"></i></div>
        </div>
        <div class="sp-overview-box" v-html="content"></div>
    </div>
</template>

<script>
import axios from "axios";
import {marked} from "marked";
export default {
    name: "UserOverview",
    data(){
        return {
            content: '',
        };
    },
    watch:{
        '$route.query.account':{
            handler() {
                this.loadData();
            },
        }
    },
    methods:{
        goEditor() {
            this.$router.push({
                name: 'overvieweditor',
            })
        },
        loadData() {
            axios.post("http://localhost:8080/api/md/readme", {id: this.$route.query.account}).then(res=>{
                if (res.data.flag === "200") {
                    if (res.data.data.mdContent === '' || res.data.data.mdContent == null) {
                        this.content = '<h1><a id="Hi_0"></a>Hi</h1> <p>…</p>';
                        return;
                    }
                    this.content = marked(res.data.data.mdContent);
                } else {
                    this.content = '<h1><a id="Hi_0"></a>Hi</h1> <p>…</p>';
                }
            });
        },
    },
    mounted() {
        this.loadData()
    }
}
</script>

<style scoped>
.sp-overview{
    width: 100%;
    font-family: ui-monospace,SFMono-Regular,SF Mono,Menlo,Consolas,Liberation Mono,monospace !important;
}
.sp-overview-box {
    width: 100%;
    border-radius: 0 0 3px 3px;
    border: 1px solid darkgray;
    border-top: none;
    padding: 5px;
    color:#80888c;
    font-size: 16px !important;
}
.sp-overview-userinfo-box {
    width: 100%;
    border-radius: 3px 3px 0 0;
    border: 1px solid darkgray;
    padding-top:  5px;
    padding-left:  5px;
    padding-right:  5px;
    border-bottom: none;
    color: #80888c;
    display: flex;
}
.sp-overview-userinfo-username {
    width: 50%;
    font-size: 12px !important;
}
.sp-overview-userinfo-edit {
    width: 50%;
    text-align: right;
    font-size: 16px;
}
.sp-overview-userinfo-edit i:hover{
    cursor: pointer;
}
</style>