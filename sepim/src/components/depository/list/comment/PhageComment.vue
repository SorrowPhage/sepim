<template>
    <div class="comment-box">
        <div class="sp-make-comment-box">
            <div class="sp-comment-avatar-box">
                <el-avatar :size="30" :src="userAvatarUrl" class="avatar"></el-avatar>
            </div>
            <div class="sp-textarea-box">
                <div class="sp-textarea-content-box">
                    <textarea class="sp-textarea-comment" v-model.lazy="substance"></textarea>
                </div>
                <div class="sp-textarea-btn-box">
                    <el-button  type="primary" @click="release"> 发布</el-button>
                </div>
            </div>
        </div>
        <div class="sp-comments-box" v-for="comment in comments" :key="comment.id">
            <div class="sp-parent-comment">
                <div class="sp-comment-avatar-box">
                    <el-avatar class="avatar_hover" :size="30"  :src="comment.avatarUrl" @click.native="goDetail(comment.account)"></el-avatar>
                </div>
                <div class="sp-comment-content-box">
                    <div class="sp-comment-user">{{comment.username}}</div>
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
                        <el-avatar class="avatar_hover" :size="30"  :src="comment.avatarUrl" @click.native="goDetail(comment.account)"></el-avatar>
                    </div>
                    <div  class="sp-comment-content-box">
                        <div class="sp-comment-user">{{comment.username}}</div>
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
import axios from "axios";
import CommentReply from "@/components/depository/list/comment/CommentReply";
export default {
    name: "PhageComment",
    data(){
        return{
            substance: '',
        }
    },
    props:['userAvatarUrl','comments'],
    components:{CommentReply},
    methods:{
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
.comment-box{
    margin-top: 30px;
    width: 100%;
    /*padding: 10px;*/
    /*background-color: dimgrey;*/
}
.sp-comment-span{
    font-weight: bold;
}
.sp-comments-box {
    width: 100%;
    border-bottom: 1px solid darkgray;
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
.sp-make-comment-box {
    width: 100%;
    display: flex;
    border-bottom: 1px solid dimgrey;
    padding-bottom: 10px;
}
.sp-textarea-box{
    width: 90%;
    display: flex;
}
.sp-textarea-comment {
    display: block;
    width: 100%;
    padding: 4px 12px;
    resize: none;
    height: 40px;
    font-size: 14px;
    line-height: 22px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-transition: height .3s ease-in-out;
    transition: height .3s ease-in-out;
    border-radius: 4px 4px 4px 4px;
    border: 1px darkgray solid;
}
.sp-textarea-content-box{
    width: 90%;
}
.sp-textarea-content-box textarea:focus{
    height: 80px;
    outline: none !important;
    border-color: #719ECE;
    box-shadow:  #719ECE;
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