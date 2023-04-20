<template>
    <div class="main">
        <div class="chat-box">
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ name:'detail',query:{account:$route.params.account} }">{{username}}</el-breadcrumb-item>
                <el-breadcrumb-item>聊天</el-breadcrumb-item>
            </el-breadcrumb>
            <div class="chat">
                <div>
                
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "PhageChat",
    data() {
        return {
            username:'',
        }
    },
    mounted() {
        this.username = this.$route.params.username;
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
    /*height: 100%;*/
    /*overflow: auto;*/
    /*background-image: linear-gradient(120deg, #155799, #159957);*/
    min-height: 540px;
    display: flex;
    justify-content: center;
    /*align-items: center;*/
}
.chat-box {
    width: 90%;
    min-width: 540px;
    max-width: 2560px;
    background: white;
    /*background-image: linear-gradient(120deg, #155799, #159957);*/
    position: relative;
}
.chat{
    width: 100%;
    margin-top: 10px;
    /*padding: 5px;*/
    height: 60%;
    border: 1px solid grey;
}
</style>