<template>
    <div>
    
    </div>
</template>

<script>
export default {
    name: "AuroraBarrage",
    data() {
        return{
            dm: [],
        }
    },
    mounted() {
    
    },
    methods:{
        dmPush(text,canvas) {
            this.dm.push({
                text: text,
                x: canvas.width,
                y: Math.random() * (canvas.height - 16) + 16,
                // color: color.value
            })
        },
    
        dmMove() {
            // 获取canvas元素
            this.canvasE = document.getElementById('c1');
            // 获取上下文
            this.ctx = this.canvasE.getContext('2d');
    
            this.ctx.clearRect(0, 0, this.canvasE.width, this.canvasE.height);
    
            for (let i = 0; i < this.dm.length; i++) {
                this.dm[i].x = this.dm[i].x - 1;
                if (this.dm[i].x < -668) {
                    this.dm.splice(i, 1);
                }
            }
            
            this.dm.forEach(item=>{
                this.ctx.font = "16px Arial";
                // this.ctx.fillStyle = item.color;
                this.ctx.fillText(item.text, item.x, item.y);
            })
        },
        
        // 先获取元素
        doLoad() {
            // 这里是视频元素我不希望用户看见，所以只生成不插入
            let video = document.createElement('video');
            // let video = document.getElementById("myVideo")
            // 指定这个视频的地址 (本地视频使用require导入)
            video.src = this.videoSrc;
        
            video.autoplay = true
            // 获取canvas元素
            this.canvasE = document.getElementById('c1');
            // 获取上下文
            this.ctx = this.canvasE.getContext('2d');
            // 保存video元素
            this.video = video
        
            console.log(this.video.duration)
            this.totalTime = this.getFormatTime(this.video.duration);
        
        
            // 给视频元素添加播放监听
            this.video.setAttribute("crossOrigin", '');//解决跨域
            video.addEventListener('play', () => {
                this.timerCallback(this.video.videoWidth / 2, this.video.videoHeight / 2);
            }, false);
        },
    
        // 用来判断视频是否被暂停或者结束
        timerCallback (width, height) {
            // 如果视频暂停或者结束就停止
            if (this.video.paused || this.video.ended) {
                return;
            }
            // 否则就就获取视频内容
            this.computeFrame(width, height);
            //重复
            setTimeout(() => {
                this.timerCallback(width, height);
            }, 0);
        },
    
        //画图
        computeFrame (width, height) {
            this.ctx.drawImage(this.video, 0, 0, width, height);
            let frame = this.ctx.getImageData(0, 0, width, height);
            let l = frame.data.length / 4;
            for (let i = 0; i < l; i++) {
                let r = frame.data[i * 4];
                let g = frame.data[i * 4 + 1];
                let b = frame.data[i * 4 + 2];
                if (g > 100 && r > 100 && b < 43)
                    frame.data[i * 4 + 3] = 0;
            }
        }
    }
}
</script>

<style scoped>

</style>