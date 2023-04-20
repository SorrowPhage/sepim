<template>
    <div class="grid-music-container">
        <audio ref="audio" @canplay='canplay' @ended="musicEnd" @timeupdate='update'>
        </audio>
        <div class="player-box">
        <div class="m-music-play-wrap">
            <div :style="animationStat" class="u-cover">
                <el-avatar :size="121" :src="pictureUrl"></el-avatar>
            </div>
            <div class="m-now-info">
                <h1>{{ musicName }} -{{ singer }}</h1>
                <div class="m-now-controls">
                    <!--进度条-->
                    <div class="progress-bar-wrapper">
                        <ProgressBar :progress=progress @progress-changing="onProgressChanging"
                                     @progress-changed='progressChanged'></ProgressBar>
                    </div>
                    <div class="u-control u-time">{{ secondsConvertedToMinutesAndSeconds(currentTime) }} /
                        {{ secondsConvertedToMinutesAndSeconds(duration) }}
                    </div>
                    
                    <div class="u-control u-volume">
                        <div v-show="volumeBar" ref="bar" class="volume">
                            <el-slider
                                v-model="vol"
                                height="50px"
                                vertical>
                            </el-slider>
                        </div>
                        <a ref="showVolumeBar" :class="{muted:mute === true}" class="volume-control"
                           @click="changeMute"></a>
                    </div>
                </div>
                <div class="m-play-controls">
                    <a class="u-play-btn prev" title="上一曲" @click="previousSong"></a>
                    <a v-show="isPlay" class="u-play-btn play" title="暂停" @click="pause"></a>
                    <a v-show="!isPlay" class="u-play-btn pause" title="播放" @click="play"></a>
                    <a class="u-play-btn next" title="下一曲" @click="nextSong"></a>
                    <a :class="{current:changeStateIndex === 1}" class="u-play-btn mode mode-list"
                       title="循环播放" @click="changeState(1)"></a>
                    <a :class="{random_current:changeStateIndex === 2}" class="u-play-btn mode mode-random"
                       title="随机播放" @click="changeState(2)"></a>
                    <a :class="{single_current:changeStateIndex === 3}" class="u-play-btn mode mode-single"
                       title="单曲循环" @click="changeState(3)"></a>
                </div>
            </div>
        </div>
        </div>
        <div class="lrc-container">
            <div ref="lyric" class="lrc-box" >
                <p v-for="(item,key,i) in $store.state.Music.lrc"
                   :key="i"
                   :class="key === rightTime ? 'active' : ''">{{item}}</p>
            </div>
        </div>
    </div>
</template>

<script>
import ProgressBar from "@/components/music/progress/ProgressBar";
import {mapMutations, mapState} from "vuex";
import axios from "axios";
import {mapGetters} from "vuex";
import $ from 'jquery';
export default {
    name: "SongPlayer",
    components: {
        ProgressBar
    },
    data() {
        return {
            offset: 30,
            rightTime: '',
            pauseAndPlay: true,
            changeStateIndex: 1,
            mute: false,
            volumeBar: false,
            // musicSrc: "http://localhost:8088/upload/sepim/music/phage.mp3",
            // audio: null,
            duration: 0, //进度条最大值
            currentTime: 0, //进度条当前值
            // isPlay: false,
            progressChanging: false,
            // musicName: "Misty Mystery",
            // singer: "GARNET CROW",
            // musicList: [],
            vol: 50,
            // pictureUrl: 'http://localhost:8088/upload/sepim/music/picture/2882585126.jpg',
            // animationState: 'animation-play-state:paused',
            // randomSongList: [],
        }
    },
    computed: {
        progress() {
            return this.currentTime / this.duration
        },
        ...mapGetters("User", ["avatar_url"]),
        ...mapState("Music", ["audio", "musicSrc", "pictureUrl", "musicName", "singer", "musicList", "isPlay"]),
        animationStat(){
            return this.isPlay ? 'animation-play-state:running' : 'animation-play-state:paused';
        }
    },
    watch: {
        vol() {
            this.audio.volume = this.vol / 100;
        },
    },
    methods: {
        ...mapMutations("Music", ['getAudio']),
        //暂停
        pause() {
            this.$store.commit("Music/changePlayAndPauseButtonState", false);
            console.log("播放暂停", this.isPlay);
            // this.isPlay = false;
            this.audio.pause();//进行暂停
            // this.animationState = 'animation-play-state:paused';
        },
        //播放
        play() {
            this.$store.commit("Music/changePlayAndPauseButtonState", true);
            console.log("播放暂停", this.isPlay);
            // this.isPlay = true;
            // this.animationState = 'animation-play-state:running';
            this.audio.play();//进行播放
        },
        //获取总时长
        canplay(e) {
            this.duration = e.target.duration
        },
        //实时更改当前时间
        update(e) {
            if (!this.progressChanging) {
                this.currentTime = e.target.currentTime
                
                let ad = this.audio;
                let time = this.secondsConvertedToMinutesAndSeconds(ad.currentTime);
                
                //当前时间在歌词中
                if (time in this.$store.state.Music.lrc) {
                    this.rightTime = time;
                    this.lyricMove();
                }
            }
        },
    
        lyricMove() {
            let active = document.getElementsByClassName("active")[0];
            let index = $(".lrc-box").children().index(active);
            let all = $(".lrc-box").children().length;
            // console.log(this.$refs.lyric.children.length)
            if (active) {
                // if (active.offsetTop >= this.offset) {
                if (index>3) {
                    // $(".lrc-box").css("transform", `translateY(-${index * this.offset}px)`)
                    this.$refs.lyric.scrollTop = (index-3) * this.offset;
                    // this.$refs.lyric.scrollTop += this.offset;
                }
                // if (index > 4) {
                //     $(".lrc-box").css("transform", `translateY(-${(index) * this.offset}px)`)
                // }
            }
        },
        //总时长秒转换为分秒
        secondsConvertedToMinutesAndSeconds(seconds) {
            let m = Math.floor((seconds / 60) % 60) < 10 ? "0" + Math.floor((seconds / 60) % 60) : Math.floor((seconds / 60) % 60);
            let s = Math.floor(seconds % 60) < 10 ? "0" + Math.floor(seconds % 60) : Math.floor(seconds % 60);
            return m + ":" + s;
        },
        onProgressChanging(e) {
            // console.log("onProgressChanging", e);
            this.progressChanging = true
            // 实时修改currentTime值
            this.currentTime = this.duration * e
        },
        //进度条发生改变
        progressChanged(e) {
            this.progressChanging = false
            this.audio.currentTime = this.currentTime = this.duration * e
            if (!this.isPlay) {
                this.isPlay = true;
                this.animationState = 'animation-play-state:running';
                this.audio.play();
            }
        },
        
        
        
        changeState(index) {
            this.changeStateIndex = index;
        },
        changeMute() {
            if (!this.volumeBar) {
                this.volumeBar = true;
            } else {
                this.mute = !this.mute;
                this.audio.muted = this.mute === true;
                // if (this.mute === true) {
                //     this.audio.muted = true;
                //     // this.audio.volume = 0;
                // } else {
                //     this.audio.muted = false
                //     // this.audio.volume = this.vol / 100;
                // }
            }
        },
        showVolumeBar(e) {
            if (!(this.$refs.bar.contains(e.target) || this.$refs.showVolumeBar.contains(e.target))) this.volumeBar = false;
        },
        
        uploadMusic() {
            axios.post("http://localhost:8080/api/music/upload", {
                musicName: "Misty Mystery", singer: "GARNET CROW", uploadTime: "2023-02-21"
            }).then(res => {
                console.log(res.data)
            })
        },
        
        getMusicList() {
            axios.post("http://localhost:8080/api/music/list").then(res => {
                if (res.data.flag === "music_get_list_success") {
                    console.log(res.data.data);
                    this.musicList = res.data.data;
                }
            })
        },
        //播放上一曲音乐
        previousSong() {
            let src = this.musicSrc;
            this.musicList.forEach(function (item, index) {
                if (src === item.serverUrl) {
                    src = index;
                }
            });
            if (typeof src === 'string') {
                this.$store.dispatch("Music/getMusicLrc",this.musicList[0].id)
                this.$store.commit("Music/changePlay", {
                    id:this.musicList[0].id,
                    musicSrc: this.musicList[0].serverUrl,
                    musicName: this.musicList[0].musicName,
                    singer: this.musicList[0].singer,
                    pictureUrl: this.musicList[0].pictureUrl,
                });
                this.audio.src = this.musicSrc;
                this.audio.play();
                // this.isPlay = true;
                this.$store.commit("Music/changePlayAndPauseButtonState", true);
            }
            else if ((src - 1) < 0) {
                this.$store.dispatch("Music/getMusicLrc",this.musicList[this.musicList.length-1].id)
                this.$store.commit("Music/changePlay", {
                    id: this.musicList[this.musicList.length - 1].id,
                    musicSrc: this.musicList[this.musicList.length - 1].serverUrl,
                    musicName: this.musicList[this.musicList.length - 1].musicName,
                    singer: this.musicList[this.musicList.length - 1].singer,
                    pictureUrl: this.musicList[this.musicList.length - 1].pictureUrl,
                });
                this.audio.src = this.musicSrc;
                this.audio.play();
                // this.isPlay = true;
                this.$store.commit("Music/changePlayAndPauseButtonState", true);
            } else {
                this.$store.dispatch("Music/getMusicLrc",this.musicList[src-1].id)
                this.$store.commit("Music/changePlay", {
                    id: this.musicList[src - 1].id,
                    musicSrc: this.musicList[src - 1].serverUrl,
                    musicName: this.musicList[src - 1].musicName,
                    singer: this.musicList[src - 1].singer,
                    pictureUrl: this.musicList[src - 1].pictureUrl,
                });
                this.audio.src = this.musicSrc;
                this.audio.play();
                // this.isPlay = true;
                this.$store.commit("Music/changePlayAndPauseButtonState", true);
            }
            
        },
        //下一曲
        nextSong() {
            let src = this.musicSrc;
            this.musicList.forEach(function (item, index) {
                if (src === item.serverUrl) {
                    src = index;
                }
            });
            if ((src + 2) > this.musicList.length || typeof src === 'string') {
                this.$store.dispatch("Music/getMusicLrc",this.musicList[0].id)
                this.$store.commit("Music/changePlay", {
                    is: this.musicList[0].id,
                    musicSrc: this.musicList[0].serverUrl,
                    musicName: this.musicList[0].musicName,
                    singer: this.musicList[0].singer,
                    pictureUrl: this.musicList[0].pictureUrl,
                });
                console.log("下一曲", this.musicSrc);
                this.audio.src = this.musicSrc;
                this.audio.play();
                // this.isPlay = true;
                this.$store.commit("Music/changePlayAndPauseButtonState", true);
            } else {
                this.$store.dispatch("Music/getMusicLrc",this.musicList[src+1].id)
                this.$store.commit("Music/changePlay", {
                    id: this.musicList[src + 1].id,
                    musicSrc: this.musicList[src + 1].serverUrl,
                    musicName: this.musicList[src + 1].musicName,
                    singer: this.musicList[src + 1].singer,
                    pictureUrl: this.musicList[src + 1].pictureUrl,
                });
                
                this.audio.src = this.musicSrc;
                this.audio.play();
                // this.isPlay = true;
                this.$store.commit("Music/changePlayAndPauseButtonState", true);
            }
        },
        musicEnd() {
            if (this.changeStateIndex === 1) {
                this.nextSong();
            } else if (this.changeStateIndex === 2) {
                console.log("随机播放");
            } else {
                this.audio.loop = true;
            }
        },
        changePlay(e) {
            this.musicName = e.name;
            this.singer = e.singer;
            this.musicSrc = e.url;
            this.pictureUrl = e.pictureUrl;
            this.$store.commit("Music/changePlay", e)
            
            this.audio.src = this.musicSrc;
            this.audio.play();
            if (!this.isPlay) {
                // this.isPlay = true;
                this.$store.commit("Music/changePlayAndPauseButtonState", true);
            }
        },
        goMusicList(e) {
            let ele = e.currentTarget;
            console.log(ele)
            // $('.typename').css('color', 'black');
            // ele.style.color('blue');
        },
        handleSelect(key, keyPath) {
            // console.log(this.$route.path)
            // console.log(key, keyPath);
        },
        
        
        // getMusicLrc() {
        //     let ad = this.audio;
        //     ad.ontimeupdate = () => {
        //
        //     };
        // },
        // transTime(time) {
        //     let minute = Math.floor(time/60);
        //     minute < 10 ? (minute = "0" + minute) : minute;
        //     let second = Math.floor(time%60);
        //     second < 10 ? (second = "0" + second) : second;
        //     return `${minute}:${second}`
        // },
        
    },
    
    mounted() {
        
        document.addEventListener('click', this.showVolumeBar);
        this.$nextTick(() => {
            this.$store.commit("Music/getAudio", this.$refs.audio);
            // this.audio = this.$refs.audio;
            // this.$store.commit("Music/getMusicSrc", this.musicSrc);
            this.audio.src = this.musicSrc;
            this.$store.dispatch("Music/getMusicList");
            this.$store.dispatch("Music/getMusicLrc", this.$store.state.Music.id);
            // this.audio.src = this.$store.state.Music.musicList[0];
            // this.audio.src = this.$store.state.Music.musicList[0];
            // this.audio.pause();
            // this.getMusicList();
            
            this.audio.pause();
            this.$store.commit("Music/changePlayAndPauseButtonState", false);
            
            // console.log(this.isPlay)
            // console.log(this.musicName)
            // console.log(this.singer)
            // console.log(this.musicSrc)
        });
    },
    
    destroyed() {
        document.removeEventListener('click', this.showVolumeBar);
    }
}
</script>

<style scoped>
.grid-music-container {
    width: 485px;
    /*width: 62%;*/
    /*height: 121px;*/
    /*height: 20%;*/
    height: 80%;
    padding: 10px;
    border-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    position: absolute;
    top: 30px;
    right: 0;
    /*left: 36%;*/
    background-color: rgba(255, 255, 255, .5);
    text-shadow: 1px 0 0 rgba(255, 255, 255, .7);
    z-index: 1;
}
.player-box {
    margin: 0 auto;
    width: 485px;
    height: 121px;
    padding: 10px;
    /*background-color: rgba(255, 255, 255, .5);*/
}
.m-music-play-wrap {
    width: 485px;
    height: 121px;
    position: relative;
    padding-left: 140px;
    /*background-color: rgba(255, 255, 255, .5);*/
}
.u-cover {
    width: 121px;
    height: 121px;
    overflow: hidden;
    background: url("~@/assets/img/music/bj.gif") 0 0 no-repeat;
    /*background-color: dimgrey;*/
    position: absolute;
    top: 0;
    left: 0;
    animation: Circle 10s linear infinite 0s forwards;
    border-radius: 50%;
}
@keyframes Circle {
    from {
        transform: rotate(0deg)
    }
    to {
        transform: rotate(360deg)
    }
}

.m-now-info {
    height: 100%;
    padding-top: 10px;
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
.m-now-controls {
    padding-top: 10px;
    position: relative;
    /*background-color: #0c0c0c;*/
    display: flex;
}
.progress-bar-wrapper {
    width: 220px;
}


.u-control {
    display: inline-block;
    vertical-align: middle;
    font-size: 0;
    overflow: hidden;
    position: relative;
    top: 7px;
}




.u-time {
    margin-left: 10px;
    font-size: 12px;
    color: #585957;
}

.u-volume {
    overflow: visible;
    text-align: center;
    position: relative;
    margin-left: 20px;
}

.volume {
    position: absolute;
    top: -54px;
    left: -8px;
}
.volume-control {
    display: inline-block;
    width: 18px;
    height: 18px;
    background: url("~@/assets/img/music/music_icons.png") -140px -80px no-repeat;
    cursor: pointer;
    
}
.user-info {
    width: 100%;
    height: 121px;
    background: #00a800;
}

.music-list {
    position: absolute;
    /*top: 171px;*/
    top: 28%;
    /*left: 415px;*/
    left: 36%;
    /*width: 100%;*/
    width: 62%;
    /*height: 319px;*/
    height: 55%;
    margin-top: 10px;
    padding: 10px;
    order-radius: 10px;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    background: rgba(255, 255, 255, .5);
    text-shadow: 1px 0 0 rgba(255, 255, 255, .7);
}
.u-process {
    width: 220px;
    height: 4px;
    position: relative;
    background-color: #cecfd4;
    cursor: pointer;
}





.volume-process {
    width: 3px;
    height: 50px;
    /*background: black;*/
    /*background: #cecfd4;*/
    position: absolute;
    top: -54px;
    left: 6px;
    cursor: pointer;
    /*visibility: hidden;*/
}



.muted {
    background-position: -158px -98px;
}

.volume-current {
    /*z-index: 1;*/
    display: inline-block;
    width: 3px;
    height: 50%;
    background-color: #3792ef;
    position: absolute;
    left: 0;
    bottom: 0;
    -webkit-transition: bottom .2s linear;
    transition: bottom .2s linear;
}

.volume-bar {
    /*z-index: 1;*/
    display: inline-block;
    width: 8px;
    height: 8px;
    border-radius: 100%;
    background-color: #fff;
    border: 1px solid #a8a9a7;
    position: absolute;
    left: -3px;
    bottom: 50%;
    -webkit-transition: bottom .2s linear;
    transition: bottom .2s linear;
}

.volume-bar:hover {
    background-color: #f1f2f0
}

.volume-event {
    width: 21px;
    left: -10px;
    /*background: white;*/
    height: 100%;
    display: inline-block;
    /*background: #3792ef 0 0;*/
    position: absolute;
    bottom: 0;
    transition: height .2s linear;
}



.m-play-controls {
    width: 300px;
    margin-top: 10px;
    /*background-color: #27ae60;*/
}

.u-play-btn {
    background: url("~@/assets/img/music/music_icons.png") -220px 0 no-repeat;
    display: inline-block;
    width: 30px;
    height: 30px;
    margin-right: 15px;
    cursor: pointer;
}

.prev {
    -webkit-transform: rotate(-180deg);
    transform: rotate(-180deg);
}

.play {
    background-position: -250px 0;
}

.pause {
    background-position: -280px 0;
}

.next {
    background-position: -220px 0;
}

.mode {
    width: 20px !important;
    height: 18px !important;
    margin-right: 10px !important;
    margin-bottom: 6px;
}


.mode-list {
    background-position: -181px -98px;
    margin-left: 65px;
    
}

.mode-random {
    background-position: -201px -80px;
}

.mode-single {
    background-position: -181px -80px;
}

.current {
    background-position: -221px -98px;
    /*background: #0c0c0c;*/
}

.random_current {
    background-position: -241px -80px;
}

.single_current {
    background-position: -221px -80px;
}
.lrc-container{
    overflow: hidden;
    height: 350px;
    width: 100%;
}
.lrc-box {
    text-align: center;
    height: 350px;
    width: 100%;
    overflow: auto;
}
.lrc-box>p {
    margin: 0;
    padding: 0;
    font-size: 16px;
    height: 30px;
    line-height: 30px;
    overflow: hidden;
    font-family: ui-monospace, SFMono-Regular, SF Mono, Menlo, Consolas, Liberation Mono, monospace !important;
}
.active {
    color: #227cf9;
    font-weight: bold;
}
</style>