<template>
    <div class="chat-line-box">
        <el-popover
            placement="top-start"
            width="150"
            trigger="click"
            :content="time">
            <div class="chat-line-content-box" slot="reference">{{content}}</div>
        </el-popover>
        <div class="chat-line-avatar-box">
            <el-avatar  :size="30"  :src="avatarUrl" ></el-avatar>
        </div>
        
<!--        <div v-show="type===1">已读</div>-->
    </div>
</template>

<script>
export default {
    name: "ChatLineRight",
    props: ['content','avatarUrl','type','time'],
}
</script>

<style scoped>
.chat-line-box {
    z-index: 99;
    font-family: "Ubuntu Light", "Ubuntu", "Ubuntu Mono", "Segoe Print", "Segoe UI", serif;
    /*border:0;*/
    /*border-bottom: none;*/
    /*padding:10px;*/
    font-size: 1.0em;
    display: flex;
    justify-content: flex-end;
    padding: 5px;
}
.chat-line-avatar-box {
    margin-right: 5px;
    width: 30px;
    /*background: dimgrey;*/
}
.chat-line-content-box {
    /*width: 90%;*/
    /*white-space: pre-wrap;*/
    margin-left: 35px;
    /*width: 100%;*/
    margin-right: 5px;
    border-radius: 3px;
    background: #227cf9;
}
</style>