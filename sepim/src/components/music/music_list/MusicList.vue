<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/index.html/music/musicmain' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>歌单</el-breadcrumb-item>
        </el-breadcrumb>
        <MusicListColumn v-for="ml in musicLists" :key="ml.id" :id="ml.id" :pictureUrl="ml.listImage" :musicListName="ml.name" :describe="ml.describe"></MusicListColumn>
    </div>
</template>

<script>
import MusicListColumn from "@/components/music/music_list/column/MusicListColumn"
import axios from "axios";
export default {
    name: "MusicList",
    components:{
        MusicListColumn,
    },
    data(){
        return{
            musicLists: [],
        }
    },
    mounted() {
        axios.post("http://localhost:8080/api/mlist/get",{owner:this.$store.state.User.account}).then(res=>{
            this.musicLists = res.data.data;
        })
    }
}
</script>

<style scoped>


</style>