<template>
    <div class="comment-box">
        <div class="sp-make-comment-headline-box">
            <div class="relpay-nav">
                <div class="relpay-title">
                    <span class="relpay-nav-headline">评论</span>
                    <span class="relpay-nav-comment-numbers">{{commentsLength}}</span>
                </div>
            </div>
            <div class="sp-make-comment-box">
                <div class="sp-textarea-box">
                    <div class="sp-comment-avatar-box">
                        <el-avatar :size="30" :src="userAvatarUrl" class="avatar"></el-avatar>
                    </div>
                    <div class="sp-textarea-content-box" >
<!--                        <textarea class="sp-textarea-comment" placeholder="Edit..." @focus="tools=true"  @blur="tools=false" v-model.lazy="substance"></textarea>-->
                        <VueEmoji class="emoji-textarea" ref="emoji"  :value="substance"  @input="onInput" width="100%" height="30"></VueEmoji>
<!--                        <div v-show="tools" class="tools-nav"  @focus="tools=true">-->
<!--                            <div>-->
<!--                            -->
<!--                            </div>-->
<!--                        </div>-->
                    </div>
                    <div class="sp-textarea-btn-box">
                        <el-button  type="primary" @click="release"> 发布</el-button>
                    </div>
                </div>
            </div>
        </div>
        
        <!--先将跟评论找出来，再找出各个跟评论下面的子评论-->
        <div class="sp-comments-box" v-for="comment in comments" :key="comment.id">
            <div class="sp-parent-comment">
                <div class="sp-comment-avatar-box">
                    <el-avatar class="avatar_hover" :size="30"  :src="comment.user.avatarUrl" @click.native="goDetail(comment.account)"></el-avatar>
                </div>
                <div class="sp-comment-content-box">
                    <div class="sp-comment-user">{{comment.user.username}}</div>
                    <div class="sp-comment-data"> 发布于 {{comment.data}}</div>
                    <div class="sp-font-family">{{comment.content}}</div>
                    <CommentReply
                        :folderId="$route.query.id"
                        :parentId="comment.id"
                        :rootId="comment.id"
                        :username="$store.state.User.userName"
                        :account="$store.state.User.account"
                        :avatarUrl="$store.state.User.avatarUrl"
                    ></CommentReply>
                </div>
            </div>
            <div v-for="comment in comment.child" :key="comment.id">
                <div class="sp-comment">
                    <div class="sp-comment-avatar-box">
                        <el-avatar class="avatar_hover" :size="30"  :src="comment.user.avatarUrl" @click.native="goDetail(comment.account)"></el-avatar>
                    </div>
                    <div  class="sp-comment-content-box">
                        <div class="sp-comment-user">{{comment.user.username}}</div>
                        <div class="sp-comment-parent-user"> @ {{comment.parentUsername}}</div>
                        <div class="sp-comment-data"> 发布于 {{comment.data}}</div>
                        <div class="sp-font-family">{{comment.content}}</div>
                        <CommentReply
                            :folderId="$route.query.id"
                            :parentId="comment.id"
                            :rootId="comment.rootId"
                            :username="$store.state.User.userName"
                            :account="$store.state.User.account"
                            :avatarUrl="$store.state.User.avatarUrl"
                        ></CommentReply>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import VueEmoji from "emoji-vue";
import axios from "axios";
import CommentReply from "@/components/depository/list/comment/CommentReply";
export default {
    name: "PhageComment",
    data(){
        return{
            substance: '',
            tools: false,
        }
    },
    props:['userAvatarUrl','comments'],
    components: {CommentReply, VueEmoji},
    computed: {
        commentsLength(){
            let length = 0;
            this.comments.forEach((e)=>{
                length = length + 1;
                if (e.child !== null) {
                    length = length + e.child.length;
                }
            })
            return length;
        }
    },
    methods:{
        onInput(event) {
            this.substance = event.data;
        },
        goDetail(account) {
            this.$router.push({
                name: 'detail',
                query:{
                    account: account,
                }
            })
        },
        release(){
            if (this.substance === '') {
                this.$message({
                    message: '内容不能为空',
                    showClose: true,
                    type: 'error',
                    center: true,
                });
                return;
            }
            axios.post("http://localhost:8080/api/md/comment/release",{
                folderId:this.$route.query.id, content: this.substance,username:this.$store.state.User.userName,
                account:this.$store.state.User.account,avatarUrl:this.$store.state.User.avatarUrl,data:this.commonDate()
            }).then(res=>{
                if (res.data.flag === "md_comment_release_succeed") {
                    this.$refs.emoji.clear();
                    this.substance = '';
                    this.$store.dispatch("Comment/getComments", this.$route.query.id);
                    this.$message({
                        message: '发布成功',
                        showClose: true,
                        type: 'success',
                        center: true,
                    });
                }
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
            return y + '-' + m + '-' + d + ' ' + hh + ":" + mm ;
        },
    
    }
}
</script>

<style scoped>
.emoji-textarea>>>.emoji-vue-textarea{
    outline: none;
}
.comment-box{
    margin-top: 30px;
    width: 100%;
    /*padding: 10px;*/
    /*background-color: dimgrey;*/
    background: white;
}
.sp-comment-span{
    font-weight: bold;
}
.sp-comments-box {
    width: 100%;
    /*border-bottom: 1px solid darkgray;*/
}
.sp-parent-comment{
    width: 100%;
    /*background-color: #227cf9;*/
    display: flex;
    padding: 5px;
    /*height: 75px;*/
}
.sp-comment-avatar-box{
    width: 5%;
    /*height: 75px;*/
}

.avatar_hover{
    height: 30px;
    border-radius: 15px;
}
.avatar_hover:hover{
    cursor: pointer;
}

.sp-comment-content-box {
    width: 95%;
    /*height: 75px;*/
    margin-bottom: 10px;
}
.sp-comment{
    width: 95%;
    margin-left: 5%;
    display: flex;
    padding: 5px;
}
.sp-make-comment-headline-box {
    width: 100%;
    /*display: flex;*/
    /*border-bottom: 1px solid dimgrey;*/
    padding: 10px;
}
.relpay-nav{
    /*font-size: 25px;*/
    height: 36px;
}
.relpay-title{
    display: flex;
    /*background: #00aaaa;*/
    align-items: center;
}
.relpay-nav-headline{
    font-family: -apple-system, BlinkMacSystemFont, Helvetica Neue, Helvetica, Arial, PingFang SC, Hiragino Sans GB, Microsoft YaHei, sans-serif;
    font-size: 24px;
    color: #18191c;
}
.relpay-nav-comment-numbers {
    margin: 0 36px 0 6px;
    font-size: 14px;
    color: #c9ccd0;
}
.sp-make-comment-box {
    width: 100%;
    padding: 5px;
    /*display: flex;*/
    /*border-bottom: 1px solid dimgrey;*/
    /*padding-bottom: 10px;*/
}
.sp-textarea-box{
    width: 90%;
    display: flex;
    align-items: center;
}
.sp-textarea-comment {
    display: block;
    width: 100%;
    /*padding: 4px 12px;*/
    resize: none;
    /*height: 40px;*/
    height: 40px;
    font-size: 20px;
    /*line-height: 22px;*/
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-transition: height .3s ease-in-out;
    transition: height .3s ease-in-out;
    border-radius: 4px 4px 4px 4px;
    /*border: 1px darkgray solid;*/
}
.sp-textarea-content-box{
    margin-left: 5px;
    width: 90%;
}
.tools-nav {
    /*width: 100%;*/
    /*background: #00aaaa;*/
    height: 25px;
    margin-top: 0;
    border: 1px solid #719ECE;
    
    /*border-radius: 4px 4px 0 0;*/
}
.sp-textarea-content-box textarea:focus{
    height: 80px;
    outline: none !important;
    border-color: #719ECE;
    border-bottom-color: white;
    box-shadow:  #719ECE;
    border-radius: 4px 4px 0 0;
}

.sp-textarea-btn-box{
    width: 10%;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
    flex-basis: 70px;
    margin-left: 10px;
    border-radius: 3px;
    cursor: pointer;
}
.sp-comment-user{
    color: darkgray;
    display: inline;
}
.sp-comment-parent-user{
    color: #0c9a9a;
    display: inline;
}
.sp-comment-data{
    display: inline;
    color: darkgray;
}
.sp-font-family{
    font-family:"Ubuntu Light","Ubuntu","Ubuntu Mono","Segoe Print","Segoe UI",serif;
}

</style>