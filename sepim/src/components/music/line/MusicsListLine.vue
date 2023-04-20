<template>
    <div class="music_list_line" @click="play">
        {{musicName}} - {{singer}}
    </div>
</template>

<script>
import {mapState} from "vuex";

export default {
    name: "MusicsListLine",
    props: {
        id:{
            type: String,
            default: "1",
        },
        musicName: {
            type: String,
            default: "Misty Mystery",
        },
        singer:{
            type: String,
            default: "GARNET CROW"
        },
        url:{
            type: String,
            default:"http://localhost:8088/upload/sepim/music/phage.mp3"
        },
        pictureUrl:{
            type: String,
            default: "http://localhost:8088/upload/sepim/music/picture/2882585126.jpg",
        }
    },
    computed:{
        ...mapState("Music", ["audio","musicSrc","isPlay"])
    },
    methods:{
        play() {
            // this.$router.push({
            //     name: 'play',
            //     query: {
            //         id: this.id,
            //     }
            // })
            this.$store.dispatch("Music/getMusicLrc",this.id)
            this.$store.commit("Music/changePlay", {
                id: this.id,
                musicSrc: this.url,
                musicName: this.musicName,
                singer: this.singer,
                pictureUrl: this.pictureUrl
            });
            this.audio.src = this.musicSrc;
            this.audio.play();
            // this.audio.play();
            if (!this.isPlay) {
                // this.isPlay = true;
                this.$store.commit("Music/changePlayAndPauseButtonState", true)
            }
        },
    }
    
}
</script>

<style scoped>
.music_list_line {
    overflow: hidden;
    width: 100%;
    height: 36px;
    line-height:36px ;
    border-bottom: 1px solid #C0C4CC;
    cursor: pointer;
    font-weight: bold;
    font-family: ui-monospace, SFMono-Regular, SF Mono, Menlo, Consolas, Liberation Mono, monospace !important;
    /*background: #227cf9;*/
}
.music_list_line:hover {
    color: #3792ef;
    background: #C0C4CC;
}
</style>