<template>
    <div>
        <MusicsListLine v-for="m in musicList"  :key="m.id" :id="m.id" :class="{sing:$store.state.Music.musicSrc===m.serverUrl}"  :pictureUrl="m.pictureUrl" :musicName="m.musicName" :singer="m.singer" :url="m.serverUrl" ></MusicsListLine>
    </div>
</template>

<script>
import MusicsListLine from "@/components/music/line/MusicsListLine";
import {mapState} from "vuex";
export default {
    name: "MusicMain",
    components:{
        MusicsListLine
    },
    computed: {
        ...mapState("Music", ['musicList'])
    },
    mounted() {
        // this.$store.dispatch("Music/getMusicList");
        this.$store.dispatch("Music/getMusicList");
    }
}
</script>

<style scoped>
.sing{
    color: #227cf9;
}
</style>