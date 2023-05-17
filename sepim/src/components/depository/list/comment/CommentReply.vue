<template>
    <div>
        <div ref="replayBtn" class="sp-reply" @click="reply()">回复</div>
        <div ref="replay" v-show="isShow" class="sp-textarea-content-box">
<!--            <textarea  ref="replay_text" v-model="content" class="sp-textarea-comment" ></textarea>-->
            <VueEmoji ref="replay_emoji" :value="content" @input="onInput" width="100%" height="30"></VueEmoji>
            <div class="sp-replay-box">
                <button class="sp-replay-btn"  @click="replayComment">评论</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import VueEmoji from "emoji-vue";
export default {
    name: "CommentReply",
    props: ['folderId', 'parentId', 'rootId', 'username', 'account', 'avatarUrl'],
    components: {VueEmoji},
    data() {
        return {
            isShow: false,
            content: '',
        }
    },
    methods: {
        onInput(event) {
            this.content = event.data;
        },
        reply() {
            this.isShow = true;
            // this.$nextTick(() => {
            //     this.$refs.replay_text.focus();
            // })
        },
        display() {
            this.isShow = false;
        },
        replayComment() {
            if (this.content === '') {
                this.$message({
                    showClose: true,
                    message: '内容为空',
                    type: 'error',
                    center: true,
                });
                return;
            }
            // this.content = this.content.replace(/\n/g, '<br/>')
            // this.content = this.content.replace(new RegExp(' ', 'gm'), '&nbsp;')
            axios.post("http://localhost:8080/api/md/comment/release", {
                folderId: this.folderId, parentId: this.parentId, rootId: this.rootId,
                username: this.username, account: this.account, avatarUrl: this.avatarUrl,
                data: this.commonDate(), content: this.content
            }).then(res => {
                this.isShow = false;
                
                this.$refs.replay_emoji.clear();
                this.content = '';
                if (res.data.flag === "md_comment_release_succeed") {
                    this.$store.dispatch("Comment/getComments", this.folderId);
                }
                this.$message({
                    showClose: true,
                    message: '回复成功',
                    type: 'success',
                    center: true,
                });
            }, Error => {
                console.log(Error.message)
            });
        },
        commonDate() {
            let date = new Date()
            let y = date.getFullYear()
            let m = date.getMonth() + 1
            m = m < 10 ? ('0' + m) : m
            let d = date.getDate()
            d = d < 10 ? ('0' + d) : d
            
            let hh = date.getHours()
            hh = hh < 10 ? ('0' + hh) : hh;
            let mm = date.getMinutes()
            mm = mm < 10 ? ('0' + mm) : mm;
            let ss = date.getSeconds()
            ss = ss < 10 ? ('0' + ss) : ss;
            return y + '-' + m + '-' + d + ' ' + hh + ":" + mm;
        },
        showUl(e) {
            // 需要使用v-show绑定
            if ((this.$refs.replayBtn.contains(e.target) || this.$refs.replayBtn.contains(e.target))) {
                this.isShow = true;
                return;
            }
            if (!(this.$refs.replay.contains(e.target) || this.$refs.replay.contains(e.target))) this.isShow = false;
        }
    },
    mounted() {
        document.addEventListener('click', this.showUl);
    },
    destroyed() {
        document.removeEventListener('click', this.showUl);
    }
}
</script>

<style scoped>
.sp-reply {
    color: darkgray;
    font-size: 14px;
    display: inline;
}

.sp-reply:hover {
    cursor: pointer;
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
    border-radius: 4px 4px 4px 4px;
    border: 1px darkgray solid;
}

.sp-textarea-content-box {
    width: 90%;
    /*display: flex;*/
    /*justify-content: center;*/
    /*align-items: center;*/
    /*justify-content: flex-end;*/
}

.sp-textarea-content-box textarea {
    width: 100%;
    height: 90px;
    overflow: auto;
    font-family:"Ubuntu Light","Ubuntu","Ubuntu Mono","Segoe Print","Segoe UI",serif;
    /*border:0;*/
    /*border-bottom: none;*/
    padding:10px;
    font-size:1.2em;
    color: rgba(34, 92, 210, 0.8);
    border:solid 1px #719ECE;
    /*margin:0 0 20px;*/
    -moz-box-shadow: inset 0 0 4px rgba(0,0,0,0.2);
    -webkit-box-shadow: inset 0 0 4px rgba(0, 0, 0, 0.2);
    /*box-shadow: inner 0 0 4px rgba(0, 0, 0, 0.2);*/
    -webkit-border-radius:  3px;
    -moz-border-radius: 3px;
    border-radius: 3px 3px 0 0;
    border-bottom: none;
    /*border-color: #719ECE;*/
    /*box-shadow:  #719ECE;*/
}

.sp-textarea-content-box textarea:focus{
    outline: none !important;
    border-color: #719ECE;
    box-shadow:  #719ECE;
    border-bottom: none;
}

.sp-replay-box {
    /*width: 90%;*/
    text-align: right;
    /*background-color: white;*/
    border: 1px solid #d2dbe3;
    border-top: none;
    border-radius: 0 0 3px 3px;
    /*padding: 10px;*/
}
.sp-replay-btn{
    color: white;
    background-color: #227cf9;
    border-color: #227cf9;
    width: 70px;
    height: 30px;
    margin-top: 5px;
    border-radius: 5px;
    margin-bottom: 5px;
    margin-right: 5px;
}
.sp-replay-btn:hover {
    cursor: pointer;
    background-image: linear-gradient(0deg, #227cf9, #3083ff)
}
</style>