<template>
    <div class="chat-line-box">
        <div class="chat-line-avatar-box">
            <el-avatar  :size="30"  :src="avatarUrl" ></el-avatar>
        </div>
        <el-popover
            placement="top-start"
           
            width="200"
            trigger="click"
            :content="time">
                <div class="chat-line-content-box" slot="reference">{{content}}</div>
        </el-popover>
<!--        <div v-show="type===1">已读</div>-->
    </div>
</template>

<script>
export default {
    name: "Chat-Line",
    props: ['content','avatarUrl','type','time'],
    computed:{
    
    }
}
</script>

<style scoped>
.chat-line-box {
    z-index: 99;
    /*background: #00aaaa;*/
    font-family: "Ubuntu Light", "Ubuntu", "Ubuntu Mono", "Segoe Print", "Segoe UI", serif;
    /*border:0;*/
    /*border-bottom: none;*/
    /*padding:10px;*/
    font-size: 1.0em;
    display: flex;
    padding: 5px;
}
.chat-line-avatar-box {
    width: 30px;
    /*background: dimgrey;*/
}
.chat-line-content-box {
    /*width: 90%;*/
    /*white-space: pre-wrap;*/
    /*width: 100%;*/
    margin-left: 5px;
    margin-right: 35px;
    border-radius: 3px;
    background: #49668d;
}
</style>