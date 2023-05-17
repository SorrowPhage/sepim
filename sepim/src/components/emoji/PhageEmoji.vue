<template>
    <div class="far_box">
        <p>
            <a href="https://github.com/shershen08/emoji-vue">引入github的表情项目包</a>
        </p>
        <div>显示内容：{{ myText }}</div>
        
        <VueEmoji ref="emoji" :value="myText" @input="onInput" />
        <button @click="clearTextarea">clear</button>
    
    </div>
</template>

<script>
import VueEmoji from 'emoji-vue'
export default {
    name: 'PhageEmoji',
    components: {
        VueEmoji
    },
    data () {
        return {
            myText: '微信表情'
        }
    },
    methods: {
        onInput (event) {
            console.log(event)
            this.myText = event.data
            // event.data contains the value of the textarea
        },
        clearTextarea () {
            this.$refs.emoji.clear()
            this.myText = ''
        }
    }
}
</script>

<style lang="less" scope>
.far_box {
    height: 100vh; //扩大盒子高度--这样在点击表情外的地方时候 触发失焦 关闭表情盒子
    text-align: left;
}
</style>

