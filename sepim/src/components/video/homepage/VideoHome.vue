<template>
    <div class="sp-video-main">
        <el-button @click="goPlay" type="primary">播放</el-button>
    </div>
</template>

<script>

export default {
    name: "VideoHome",
    data(){
        return{
        
        }
    },
    methods:{
        goPlay() {
            this.$router.push({
                name:'aurora',
            })
        },
    }
}
</script>

<style scoped>
.sp-video-main {
    margin: 0 auto;
    padding: 0;
    width: 90%;
    max-width: 2026px;
    /*background: #e0ac16;*/
}


</style>