<template>
    <div>
<!--        <div class="tools-box">-->
<!--            xsad-->
<!--        </div>-->
        <div class="content-box">
            <textarea rows="4"  ref="replay_text" v-model="content" class="sp-textarea-comment" ></textarea>
        </div>
        <div class="btn-box">
            <div class="btn-content-box">
                <el-button size="mini" type="primary" >发送</el-button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "ChatReply",
    data(){
        return{
            content: '',
        }
    },
    methods:{
        send(){
        
        }
    }
}
</script>

<style scoped>
.tools-box {
    margin-top: 5px;
    height: 10px;
    width: 100%;
    border: 1px solid dimgrey;
}

.content-box{
    /*margin-top: 20px;*/
    /*height: 280px;*/
}
.sp-textarea-comment {
    display: block;
    width: 100%;
    padding: 4px 12px;
    resize: none;
    /*height: 40px;*/
    font-size: 14px;
    line-height: 22px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-transition: height .3s ease-in-out;
    transition: height .3s ease-in-out;
    /*border-radius: 4px 4px 4px 4px;*/
    border-bottom: 1px gray solid;
}
textarea{
    outline: none;
}

.btn-box{
    margin-top: 5px;
    text-align: right;
}
.btn-content-box {
    margin-right: 5px;
}
</style>