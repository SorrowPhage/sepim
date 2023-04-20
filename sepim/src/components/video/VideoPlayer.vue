<template>
    <div class="video-box">
            <!--导航栏-->
        <VideoNavigationBar></VideoNavigationBar>
        <VideoHome></VideoHome>
    </div>
</template>

<script>
import VideoNavigationBar from "@/components/video/nav/VideoNavigationBar";
import VideoHome from "@/components/video/homepage/VideoHome";
export default {
    name: "VideoPlayer",
    components:{
        VideoNavigationBar,
        VideoHome
    }
}
</script>

<style scoped>
.video-box {
    margin-top: 50px;
    position: relative;
}


</style>