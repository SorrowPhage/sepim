<template>
    <div class="user_line_box" @click="goChat()">
        <div>
            <el-avatar class="avatar_hover" :size="40"  :src="avatarUrl" ></el-avatar>
        </div>
        <div class="content_box">
            <div class="info-box">
                <div class="username-box">{{username}}</div>
                <div class="time-box">{{time}}</div>
            </div>
            <div class="message-box" slot="reference">
                <span>{{lastMessage}}</span>
                <div v-show="num" class="badge-box">
                    {{num}}
                </div>
<!--                <el-badge :value="num" class="item">-->
<!--                -->
<!--                </el-badge>-->
            </div>

        </div>
        
    </div>
</template>

<script>
export default {
    name: "ChatUserLine",
    props: ['lastMessage', 'avatarUrl','username','time','account','num'],
    data() {
        return{
        
        }
    },
    methods:{
        goChat() {
            this.$router.push({
                name: 'chat',
                params:{
                    account: this.account,
                    username: this.username,
                    url: this.avatarUrl,
                }
            })
        },
    }
}
</script>

<style scoped>
.user_line_box {
    display: flex;
    padding: 10px;
    margin-top: 5px ;
}
.user_line_box:hover{
    background: rgba(105, 105, 105, 0.3);
    cursor: pointer;
}
.content_box {
    width: 100%;
    margin-left: 5px;
    white-space: nowrap;
    overflow: hidden;
    /*color: dimgrey;*/
    text-overflow: ellipsis;
}
.info-box {
    display: flex;
    width: 100%;
}
.username-box {
    width: 50%;
}
.time-box {
    width: 50%;
    text-align: right;
    color: dimgrey;
    font-size: 1px;
}
.message-box{
    white-space: nowrap;
    overflow: hidden;
    color: dimgrey;
    text-overflow: ellipsis;
}

.badge-box {
    /*width: 20px;*/
    position: relative;
    float: right;
    /*display: inline;*/
    background-color: #f56c6c;
    border-radius: 10px;
    color: #fff;
    display: inline-block;
    font-size: 12px;
    height: 18px;
    line-height: 18px;
    padding: 0 6px;
    text-align: center;
    white-space: nowrap;
}
/*.item {*/
/*    !*margin-top: 10px;*!*/
/*    margin-right: 40px;*/
/*}*/


</style>