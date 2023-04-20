<template>
    <div class="main">
        <div class="video-container">
            <div class="player" @mouseenter="moserOver" @mouseleave="moserRemove" @mousemove="mouseMove">
                <div style="min-width: 668px">
                    <canvas ref="c1" id="c1" class="-bwp-internal-render-canvas"></canvas>
                    <video id="myVideo" ref="video" :src="videoSrc" class="-bwp-internal-proxy-video-element"
                    ></video>
<!--                    @oncanplay="playVideo"  @ontimeupdate="updateTime" @onended="endVideo"-->
                </div>
                <div class="controls" v-show="isShow">
                    <div class="progress-box">
                        <!-- 进度条 -->
                        <div ref="progress" class="progress" @click="jumpProgress">
                            <div ref="loaded" id="loaded" class="loaded"></div>
                            <div class="line"></div>
                            <div class="bar"></div>
                        </div>
                    </div>
                    <!-- 播放/暂停 -->
                    <a ref="isPlay" @click="playAndPause" class="switch"></a>
                    <!-- 全屏 -->
                    <a ref="expand" class="expand" @click="fullScreen"></a>
                    <!-- 时间 -->
                    <div class="timer">
                        <span ref="currPlayTime" class="current">{{currPlayTime}} / </span>
                        <span ref="totalTime" class="total">{{totalTime}}</span>
                    </div>
                </div>
                
                <div class="bottom-controls"></div>
            </div>
            
            <div class="right-container">
                back
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "PhageAurora",
    data() {
        return {
            isShow: false,
            totalTime: null,
            currPlayTime: '00:00:00',
            videoSrc: 'http://localhost:8088/upload/sepim/video/1038009205_nb3-1-16.mp4',
            dmList: [{text: '这是一条弹幕', x: 300, y: 30}, {text: '这是第二条弹幕', x: 300, y: 90}, {
                text: '这是第三条弹幕',
                x: 300,
                y: 60
            }],
            video: null,
            
        };
    },
    computed: {
    
    },
    
    methods: {
        dmMove() {
            // 获取canvas元素
            let canvasE = document.getElementById('c1');
            // 获取上下文
            let ctx = canvasE.getContext('2d');
            
            
            this.q = setInterval(() => {
                ctx.clearRect(0, 0, canvasE.width, canvasE.height);
                
                this.dmList.forEach(item => {
                    ctx.fillStyle = 'green';
                    ctx.font = '10px 微软雅黑';
                    item.x -= 1;
                    ctx.fillText(item.text, item.x, item.y);
                })
            }, 1000 / 60);
        },
        
        
        // 进入视频状态
        videoPlay() {
            let self = this
            
            // 当视频可以播放的时候
            this.$refs.video.oncanplay = function () {
                this.style.display = "block";                        // 显示视频
                self.totalTime = self.getFormatTime(this.duration);  // 显示视频总时长
                
            };
            
            // 播放进度
            this.$refs.video.ontimeupdate = function () {
                let currTime = this.currentTime;
                let duration = this.duration;
                
                let pre = (currTime / duration) * 100 + "%";         // 百分比
                
                let loaded = document.getElementById("loaded");
                loaded.style.width = pre;                            // 显示进度条
                
                self.currPlayTime = self.getFormatTime(currTime);    // 显示当前播放进度时间
                
            };
            
            // 播放完毕还原设置
            this.$refs.video.onended = function () {
                let loaded = document.getElementById("loaded");
                loaded.style.width = "0%";                           //进度条为0
                this.currentTime = 0
                // clearTimeout(self.q);
            };
        },
    
        playVideo() {
            this.$refs.video.style.display = 'block';
    
            this.totalTime = this.getFormatTime(this.$refs.video.currentTime);
        },
    
        updateTime() {
            let pre = (this.currPlayTime / this.totalTime) * 100 + "%";
            let loaded = document.getElementById("loaded");
            loaded.style.width = pre;                            // 显示进度条
    
            this.currPlayTime = this.getFormatTime(this.$refs.video.currentTime);    // 显示当前播放进度时间
            if (this.currPlayTime === "00:00:05") {
                this.dmMove();
            }
        },
    
        endVideo() {
            let loaded = document.getElementById("loaded");
            loaded.style.width = "0%";                           //进度条为0
            this.$refs.video.currentTime = 0
        },
        
        // 获取视频总时长
        getFormatTime(time) {
            const t = time || 0;
            let h = parseInt(t / 3600);
            let m = parseInt((t % 3600) / 60);
            let s = parseInt(t % 60);
            h = h < 10 ? "0" + h : h;
            m = m < 10 ? "0" + m : m;
            s = s < 10 ? "0" + s : s;
            return h + ":" + m + ":" + s;
        },
        
        // 播放/暂定
        playAndPause() {
            this.dmMove();
            let myVideo = document.getElementById("myVideo");
            if (myVideo.paused) {
                myVideo.play();
            } else {
                myVideo.pause();
            }
        },
        
        // 跳跃播放
        jumpProgress(e) {
            let myVideo = document.getElementById("myVideo");
            var event = e || window.event;
            myVideo.currentTime =
                (event.offsetX / this.$refs.progress.offsetWidth) * myVideo.duration;
        },
        
        // 全屏
        fullScreen(){
            this.$refs.video.webkitRequestFullScreen();
        },
    
        
        moserOver() {
            this.isShow = true;
        },
        moserRemove() {
            this.isShow = false
            // clearTimeout(this.delay_loading_qr)
        },
        mouseMove() {
            this.isShow = true;
            // this.delay_loading_qr = setTimeout(() => {
            //     this.isShow = false
            // }, 2000)
        },
    
        
        
      
    },
    mounted() {
        this.videoPlay();
        // this.doLoad();
    },
};
</script>

<style  scoped>
.main {
    margin-top: 50px;
    height: 100%;
    width: 100%;
    padding: 0;
    background: white;
    position: fixed;
}

@keyframes bganimation {
    0% {
        background-position: 0 50%;
    }
    
    50% {
        background-position: 100% 50%;
    }
    
    100% {
        background-position: 0 50%;
    }
}

.video-container{
    margin: 0 auto;
    display: flex;
    justify-content: center;
    box-sizing: content-box;
    position: relative;
    /*background: #227cf9;*/
    width: auto;
    padding: 0 10px
}

a {
    text-decoration: none;
}



.player {
    /*width: 720px;*/
    /*width: 75%;*/
    /*width: var(--my-width);*/
    /*height: 360px;*/
    /*height: 404px;*/
    /*height: var(--my-height);*/
    /*width: 668px;*/
    margin: 0 auto;
    background-size: 48px 48px;
    background-repeat: no-repeat;
    position: relative;
}
video {
    display: block;
    width: 100%;
    height: 100%;
    object-fit: fill;
    margin: 0 auto;
    background: black;
}
.controls {
    /*width: 720px;*/
    width: 100%;
    height: 40px;
    /*background-color: #2196f3;*/
    position: absolute;
    left: 0;
    bottom: 0;
    z-index: 1000;
    opacity: 0.7;
    /*background: rgba(128, 128, 128, 0.1);*/
}
.switch {
    display: block;
    width: 20px;
    height: 20px;
    font-size: 20px;
    color: #fff;
    position: absolute;
    top: 10px;
    /*left: 10px;*/
    left: 2%;
    /*background-image: url("~@/assets/img/video/play.png");                   !* 播放按钮 *!*/
    background-size: 20px 20px;
    background: white no-repeat;
}
.switch:hover {
    color: blue;
    cursor: pointer;
}
.expand {
    display: block;
    width: 20px;
    height: 20px;
    font-size: 20px;
    color: #fff;
    position: absolute;
    /*right: 10px;*/
    right: 2%;
    top: 10px;
    /*background-image: url(../assets/video/expand.png);*/
    background-size: 20px 20px;
    background: white no-repeat;
}
.expand:hover {
    color: blue;
    cursor: pointer;
}
.progress {
    /*width: 430px;*/
    /*width: 700px;*/
    height: 8px;
    /*left: 10px;*/
    width: 96%;
    left: 2%    ;
    border-radius: 1px;
    overflow: hidden;
    background-color: rgba(85, 85, 85, 0.5);
    cursor: pointer;
    position: absolute;
    /*top: 16px;*/
    /*left: 45px;*/
}
.loaded {
    position: absolute;
    bottom: 0;
    width: 0;
    /*height: 100%;*/
    height: 3px;
    /*background-color: blue;*/
    background-color: #409EFF;
}
.line {
    width: 0;
    height: 100%;
    background-color: #fff;
    position: absolute;
    top: 0;
    left: 0;
}
.bar {
    width: 100%;
    height: 100%;
    opacity: 0;
    position: absolute;
    left: 0;
    top: 0;
    z-index: 1;
}
.timer {
    height: 20px;
    line-height: 20px;
    position: absolute;
    /*left: 490px;*/
    left: 10%;
    top: 11px;
    color: #fff;
    font-size: 14px;
}
.bottom-controls {
    width: 100%;
    height: 40px;
    /*background-color: #2196f3;*/
    position: absolute;
    left: 0;
    /*bottom: -40px;*/
    /*margin-top: 40px;*/
    z-index: 99;
    opacity: 0.7;
    background: #f1f2f3;
    box-shadow: 1px 1px 1px rgba(128, 128, 128, 0.5);
}

.right-container{
    width: 411px;
    flex: none;
    margin-left: 30px;
    position: relative;
    pointer-events: none;
}

.-bwp-internal-render-canvas {
    z-index: 999;
    width: 100%;
    /*height: 100%;*/
    aspect-ratio: 100/56.2;
    display: block;
    position: relative;
    object-fit: inherit;
}

.-bwp-internal-proxy-video-element {
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    aspect-ratio: 100/56.2;
    /*display: none;*/
    position: absolute;
    margin: inherit;
    padding: inherit;
    object-fit: inherit;
}
</style>
