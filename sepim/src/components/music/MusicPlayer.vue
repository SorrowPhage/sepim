<template>
    <div class="main">
        <div class="music_program">
            <div class="user-panel">
                <!--                <div class="music_user_panel">-->
                <!--                    <el-avatar :size="121" :src="avatar_url"></el-avatar>-->
                <!--                </div>-->
                <div class="navigation_bar">
                    <!--                    <div class="navigation_column">-->
                    <!--                        <div style="display: inline">-->
                    <!--                            <el-menu-->
                    <!--                                :default-active="this.$route.path"-->
                    <!--                                :router="true"-->
                    <!--                                active-text-color="#398bff"-->
                    <!--                                class="el-menu-demo"-->
                    <!--                                mode="horizontal"-->
                    <!--                                text-color="#000"-->
                    <!--                                @select="handleSelect">-->
                    <!--                                <el-menu-item index="/index.html/music/musicmain">首页</el-menu-item>-->
                    <!--                                <el-menu-item index="/index.html/music/musiclist">歌单</el-menu-item>-->
                    <!--                            </el-menu>-->
                    <!--                        </div>-->
                    <!--                    </div>-->
                    
                    <div class="navigation_column">
                        <div class="sp-nav" @click="goMusicMain">首页</div>
                        <div class="sp-nav" @click="goMusicList">歌单</div>
                    </div>
                    
                    <!--                    <div class="nav-box">-->
                    <!--                        <el-menu-->
                    <!--                            class="el-menu-vertical-demo"-->
                    <!--                            default-active="2"-->
                    <!--                        >-->
                    <!--                            <el-menu-item-group>-->
                    <!--                                <template slot="title">分组一</template>-->
                    <!--                                <el-menu-item index="1-1">ping</el-menu-item>-->
                    <!--                                <el-menu-item index="1-2">选项2</el-menu-item>-->
                    <!--                            </el-menu-item-group>-->
                    <!--                            <el-menu-item-group title="分组2">-->
                    <!--                                <el-menu-item index="1-3">选项3</el-menu-item>-->
                    <!--                                <el-menu-item index="1-4">选项4</el-menu-item>-->
                    <!--                            </el-menu-item-group>-->
                    <!--                        </el-menu>-->
                    <!--                    </div>-->
                    
                    <div class="content_panel">
                        <router-view></router-view>
                    </div>
                </div>
            </div>
            <song-player></song-player>
        </div>
    </div>
</template>

<script>
import SongPlayer from "@/components/music/player/SongPlayer"

export default {
    name: "MusicPlayer",
    components: {
        SongPlayer
    },
    methods: {
        goMusicMain() {
            this.$router.push({
                name: 'musicmain'
            })
        },
        goMusicList() {
            this.$router.push({
                name: 'musiclist'
            })
        },
        handleSelect(key, keyPath) {
            // console.log(this.$route.path)
            // console.log(key, keyPath);
        },
    },
}
</script>

<style scoped>
.main {
    margin-top: 50px;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    padding: 0;
    background-image: linear-gradient(
        125deg,
        #2c3e50,
        #27ae60,
        #2980b9,
        #eb4d4b,
        #2c3e50
    );
    background-size: 400%;
    animation: bganimation 15s infinite;
    position: fixed;
}

@keyframes bganimation {
    0% {
        background-position: 0% 50%;
    }
    
    50% {
        background-position: 100% 50%;
    }
    
    100% {
        background-position: 0% 50%;
    }
}

.music_program {
    width: 90%;
    height: 100%;
    /*background-color: white;*/
    position: relative;
    /*left: 10%;*/
    /*border: 1px solid #227cf9;*/
}

.user-panel {
    /*width: 385px;*/
    /*width: 33%;*/
    width: 50%;
    /*height: 450px;*/
    height: 80%;
    /*height: 121px;*/
    padding: 10px;
    order-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    position: absolute;
    /*left: 10%;*/
    top: 30px;
    background-color: rgba(255, 255, 255, .5);
    /*background: white;*/
    text-shadow: 1px 0 0 rgba(255, 255, 255, .7);
}

.music_user_panel {
    height: 25%;
    /*background: #227cf9;*/
    text-align: center;
    width: 100%;
}

.navigation_bar {
    /*background-color: rgba(87, 82, 82, 0.062);*/
    /*margin-top: 5%;*/
    height: 71%;
}

.navigation_column {
    width: 100%;
    border-bottom: 1px solid white;
    /*height: 10px;*/
    /*background: rgba(87, 82, 82, 0.062);*/
    /*background: #409EFF;*/
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.05);
    /*padding: 10px;*/
}

.sp-nav {
    display: inline;
    margin-left: 10px;
    font-weight: bold;
    font-family: ui-monospace, SFMono-Regular, SF Mono, Menlo, Consolas, Liberation Mono, monospace !important;
    /*color: #5a8da1;*/
    color: black;
    font-size: 16px !important;
}

.sp-nav:hover {
    cursor: pointer;
    color: #409EFF;
    /*color: black;*/
}

.content_panel {
    height: 88%;
    /*background: #00a800;*/
    padding: 10px;
}

h1 {
    margin: 0;
    padding: 0;
    -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
    word-wrap: break-word;
    font-size: inherit;
    line-height: inherit;
    overflow: visible;
}


.sing {
    color: #227cf9;
}

.nav-box {
    width: 150px;
}

</style>