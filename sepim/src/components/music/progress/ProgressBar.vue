<template>
        <div class="progress-bar">
            <!-- 后面黑色的一条 -->
            <div class="bar-inner" @click="clickProgress">
                <!-- 已经播放的区域 -->
                <div class="progress" :style='progressStyle' ref="progress">
                </div>
                <!-- btn -->
                <div class="progress-btn-wrapper" :style='btnStyle' @touchstart='onTouchStart'
                     @touchmove='onTouchMove' @touchend='onTouchEnd' >
                    <div class="progress-btn"></div>
                </div>
            </div>
        </div>
</template>

<script>
export default {
    name: "ProgressBar",
    props: {
        progress: {
            type: Number,
            default: 0
        }
    },
    data(){
        return{
            offset: 0,
            progressBtnWidth: 16,
        }
    },
    computed: {
        //进度条样式
        progressStyle() {
            return `width: ${this.offset}px`
        },
        //
        btnStyle() {
            return `transform: translate3d(${this.offset}px,0,0)`
        },
    },
    watch: {
        progress(newProgress) {
            // 进度条宽度
            const barWidth = this.$el.clientWidth - this.progressBtnWidth
            this.offset = barWidth * newProgress
        }
    },
    methods:{
        onTouchStart(e) {
            // console.log(e);
            this.touch.x1 = e.changedTouches[0].clientX
            // 黄色进度条初始宽度
            this.touch.beginWidth = this.$refs.progress.clientWidth
            console.log(this.touch);
        },
        onTouchMove(e) {
            // console.log(e);
            // x偏移量
            const delta = e.changedTouches[0].clientX - this.touch.x1
            // 之前的width+这次拖动增加的偏移量=应有的黄条长度
            const tempWidth = this.touch.beginWidth + delta
            // 再拿到barWidth
            const barWidth = this.$el.clientWidth - this.progressBtnWidth
            // 黄条长度/barwidth = progress 现在应该有的进度
            const progress = tempWidth / barWidth
            this.offset = barWidth * progress
            this.$emit('progress-changing', progress);
            // console.log("tempWidth", tempWidth);
            // console.log("barWidth", barWidth);
            // console.log("progress", progress);
        
        },
        onTouchEnd(e) {
            // console.log(e);
            const barWidth = this.$el.clientWidth - this.progressBtnWidth
            const progress = this.$refs.progress.clientWidth / barWidth
            this.$emit('progress-changed', progress)
        },
        // 点击进度条
        clickProgress(e){
            // console.log("fds");
            console.log('getBoundingClientRect', this.$el.getBoundingClientRect());
            const rect = this.$el.getBoundingClientRect()
            // 黄条应有的宽度
            const offsetWidth = e.pageX - rect.x
            const barWidth = this.$el.clientWidth - this.progressBtnWidth
            const progress = offsetWidth/barWidth
            this.$emit('progress-changed', progress);
            console.log(offsetWidth)
        },
    },
    created() {
        this.touch = {}
    },
    
}
</script>

<style scoped>

.progress-bar {
    height: 30px;
    cursor: pointer;
}

.bar-inner {
    position: relative;
    top: 11px;
    height: 8px;
    background-color: rgba(87, 82, 82, 0.062);
    border-radius: 5px;
}

.progress {
    position: absolute;
    height: 100%;
    background-color: #3792ef;
    
}

.progress-btn-wrapper {
    position: absolute;
    left: -8px;
    top: -11px;
    width: 30px;
    height: 30px;
}

.progress-btn {
    position: relative;
    top: 7px;
    left: 7px;
    box-sizing: border-box;
    width: 16px;
    height: 16px;
    border: 3px solid white;
    border-radius: 50%;
    background: white;
}
</style>