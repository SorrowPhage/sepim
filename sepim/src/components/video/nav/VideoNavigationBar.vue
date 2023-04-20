<template>
    <div>
        <div class="header-bar">
            <left-navi></left-navi>
            <video-search-bar></video-search-bar>
        </div>
        <div class="header-banner">
        
        </div>
        <div class="header__channel">
            <div class="channel-icons">
                <div class="channel-icons__item ">
                    <div class="sp-icon-wrap">
                        <div class="icon-bg">
                            <span class="sp-icon-box el-icon-s-fold"></span>
                        </div>
                        <div class="icon-title">
                            动态
                        </div>
                    </div>
                </div>
                <div class="channel-icons__item ">
                    <div class="sp-icon-wrap">
                        <div class="icon-bg">
                            <span class="sp-icon-box el-icon-collection-tag"></span>
                        </div>
                        <div class="icon-title">
                            热门
                        </div>
                    </div>
                </div>
                <div class="channel-icons__item ">
                    <div class="sp-icon-wrap">
                        <div class="icon-bg">
                            <span class="sp-icon-box el-icon-picture-outline-round"></span>
                        </div>
                        <div class="icon-title">
                            频道
                        </div>
                    </div>
                </div>
            </div>
            <div class="right-channel-container">
                <div class="channel-items__left">
                    <div class="channel-link">番剧</div>
                    <div class="channel-link">番剧</div>
                    <div class="channel-link">番剧</div>
                    <div class="channel-link">番剧</div>
                    <div class="channel-link">番剧</div>
                </div>
                <div class="channel-items__right">
                    <div class="channel-link__right">
                        <div class="side-icon el-icon-folder"></div>
                        <span>专栏</span>
                    </div>
                    <div class="channel-link__right">
                        <div class="side-icon el-icon-folder"></div>
                        <span>专栏</span>
                    </div>
                    <div class="channel-link__right">
                        <div class="side-icon el-icon-folder"></div>
                        <span>专栏</span>
                    </div>
                    <div class="channel-link__right">
                        <div class="side-icon el-icon-folder"></div>
                        <span>专栏</span>
                    </div>
                    <div class="channel-link__right">
                        <div class="side-icon el-icon-folder"></div>
                        <span>专栏</span>
                    </div>
                    <div class="channel-link__right">
                        <div class="side-icon el-icon-folder"></div>
                        <span>专栏</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import LeftNavi from "@/components/video/nav/search/LeftNavi";
import VideoSearchBar from "@/components/video/nav/search/VideoSearchBar";
export default {
    name: "VideoNavigationBar",
    components:{
        LeftNavi, VideoSearchBar
    }
}
</script>

<style scoped>
* {
    box-sizing: border-box;
}
.header-bar{
    position: absolute;
    top: 0;
    z-index: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 24px;
    max-width: 2560px;
    width: 100%;
    height: 64px;
    
    /*background: #27ae60;*/
    /*background: #ffffff;*/
}
.header-banner {
    position: relative;
    /*position: absolute;*/
    /*top: 50px;*/
    z-index: 0;
    display: flex;
    -ms-flex-pack: center;
    justify-content: center;
    margin: 0 auto;
    min-width: 1020px;
    min-height: 155px;
    height: 9.375vw;
    max-height: 240px;
    /*background-color: #e3e5e7;*/
    background-size: cover;
    background: url("~@/assets/img/video/preview.jpg") no-repeat center 0;
    background-size: 100%;
}
.header__channel {
    margin: 0 auto;
    /*padding: 0 var(--layout-padding);*/
    padding: -25px;
    height: 100px;
    /*background: #00a800;*/
    width: 90%;
    max-width: 2026px;
    
    display: flex;
    align-items: center;
    background: #ffffff;
    position: relative;
    
}

.channel-icons {
    z-index: 0;
    display: flex;
    align-items: center;
    margin-right: 8px;
}
.channel-icons__item {
    position: relative;
    display: flex;
    flex-direction: column;
    margin-right: 24px;
    color: #18191c;
}
.sp-icon-wrap{
    cursor: pointer;
    position: relative;
    display: flex;
    flex-direction: column;
    margin-right: 16px;
    color: #18191c;

}
.icon-bg {
    width: 46px;
    height: 46px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 6px;
    border-radius: 50%;
    transition: background .3s;
    background: #e0ac16;
}
.sp-icon-box{
    width: 21px;
    height: 21px;
}

.icon-title {
    font-size: 14px;
    line-height: 20px;
    text-align: center;
}
.right-channel-container {
    width: 100%;
    display: flex;
    justify-content: space-between;
}
.channel-items__left {
    display: grid;
    width: 100%;
    grid-auto-flow: column;
    grid-column: span 4;
    grid-gap: 10px;
    grid-template-rows: repeat(2,1fr);
    border-right: 1px solid #e3e5e7;
    position: relative;
    grid-template-columns: repeat(11,1fr);
    padding-right: 30px;
}
.channel-link {
    display: inline-block;
    box-sizing: content-box;
    width: 100%;
    /*height: 100%;*/
    border: 1px solid #f1f2f3;
    border-radius: 6px;
    background-color: #f6f7f8;
    color: #61666d;
    text-align: center;
    font-weight: 400;
    transition: background-color .3s,color .3s;
    height: 26px;
    line-height: 26px;
    font-size: 13px;
    letter-spacing: 2px;
    cursor: pointer;
}
.channel-items__right {
    grid-column: span 1;
    grid-row-gap: 10px;
    grid-auto-flow: column;
    display: grid;
    grid-template-rows: repeat(2,1fr);
    width: 168px;
    flex-shrink: 0;
}
.channel-link__right {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    width: 100%;
    /*height: 100%;*/
    color: #61666d;
    text-align: center;
    font-weight: 400;
    transition: background-color .3s,color .3s;
    letter-spacing: 2px;
    font-size: 13px;
    height: 28px;
    cursor: pointer;
}
.side-icon {
    margin-right: 4px;
    width: 20px;
    height: 20px;
    fill: #61666d;
    transition: fill .3s;
}
</style>