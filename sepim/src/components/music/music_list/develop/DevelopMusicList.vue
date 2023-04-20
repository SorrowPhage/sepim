<template>
    <div class="ba">
        <div class="fence">
<!--            <div class="el-icon-back arrowhead" @click="prve"></div>-->
            <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item :to="{ path: '/index.html/music/musicmain' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{path:'/index.html/music/musiclist'}">歌单</el-breadcrumb-item>
                <el-breadcrumb-item>{{name}}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <MusicsListLine v-for="m in $store.state.Music.musicList"  :key="m.id" :id="m.id" :class="{sing:$store.state.Music.musicSrc===m.serverUrl}"  :pictureUrl="m.pictureUrl" :musicName="m.musicName" :singer="m.singer" :url="m.serverUrl" ></MusicsListLine>
        </div>
    </div>
</template>

<script>
import MusicsListLine from "@/components/music/line/MusicsListLine";
import pubsub from 'pubsub-js'
import axios from "axios";
export default {
    name: "DevelopMusicList",
    components:{
        MusicsListLine,
    },
    data() {
        return{
            // music_list: this.$store.state.Music.musicList,
            // music_list:  [],
            name: '',
        }
    },
    methods:{
        prve() {
            // pubsub.publish("isDev", true);
            this.$router.back();
        },
    },
    mounted() {
        // this.$store.dispatch();
        this.$store.dispatch("Music/getMusicListByMusicListId",this.$route.query.id);
        axios.post("http://localhost:8080/api/list/name", {id:this.$route.query.id}).then(res=>{
            this.name = res.data.data.name;
        });
    }
}
</script>

<style scoped>
.ba{
    background: rgba(246, 248, 250, 0.2);
}
.fence{
    height: 16px;
    background: rgba(246, 248, 250, 0.2);
    width: 100%;
}
.arrowhead{
    cursor: pointer;
}
.sing{
    color: #227cf9;
}
</style>