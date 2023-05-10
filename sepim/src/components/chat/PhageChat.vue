<template>
    <div class="main">
        <div class="chat-box">
            <div class="info-box">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item :to="{ name:'detail',query:{account:$route.params.account} }">{{ username }}
                    </el-breadcrumb-item>
                    <el-breadcrumb-item>聊天</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="chat">
                <div ref="display-box" class="content-box" >
                    <div v-for="(m,index) in messags" :key="index">
                        <ChatLineRight v-if="m.fromId===account" :avatar-url="m.user.avatarUrl" :content="m.content" :type="m.type" :time="formatDate(m.sendTime)"></ChatLineRight>
                        <chat-line v-else :avatarUrl="m.user.avatarUrl" :content="m.content" :type="m.type" :time="formatDate(m.sendTime)"></chat-line>
                    </div>
                </div>
                <div class="replay-box">
<!--                    <chat-reply></chat-reply>-->
                    <div class="reply-content-box">
                        <textarea rows="4"  ref="replay_text" v-model="contentReply" class="sp-textarea-comment" ></textarea>
                    </div>
                    <div class="btn-box">
                        <div class="btn-content-box">
                            <el-button size="mini" type="primary" @click="send" >发送</el-button>
                        </div>
                    </div>
                </div>
            </div>
           
        </div>
    </div>
</template>

<script>
import ChatLine from "@/components/chat/Chat-Line";
import ChatLineRight from "@/components/chat/ChatLineRight";
import {mapState} from "vuex";
let socket;
export default {
    name: "PhageChat",
    components: {
        ChatLine,ChatLineRight
    },
    computed: {
        ...mapState("User", ['account', 'avatarUrl']),
        // ...mapState("ChatMessage", [ 'messages'])
    },
    data() {
        return {
            username: '',
            messags: [],
            toUser: this.$route.params.account,
            contentReply:''
        }
    },
    created() {
        this.$store.dispatch("ChatMessage/getChatMessageList", {s1: this.$route.params.account, s2: this.account});
        this.messags = this.$store.state.ChatMessage.messages;
        this.username = this.$route.params.username;
        this.init();
    },
    methods: {
        formatDate(sendTime) {
            let a = new Date(sendTime).getTime();
            const date = new Date(a);
            const Y = date.getFullYear() + '/';
            const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/';
            const D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + '  ';
            const h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
            const m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
            // const s = date.getSeconds(); // 秒
            // console.log('dateString', dateString); // > dateString 2021-07-06 14:23
            return Y + M + D + h + m;
        },
        send(){
            if (this.contentReply === '' || this.contentReply == null) {
                console.log("未输入内容")
            } else {
                let message = {
                    fromId: this.account,
                    toId: this.$route.params.account,
                    content: this.contentReply
                };
                
                socket.send(JSON.stringify(message));
                this.messags.push({
                    fromId: this.account,
                    toId: this.$route.params.account,
                    content: this.contentReply,
                    user:{avatarUrl: this.$route.params.url}
                });
                
                this.contentReply = '';
                this.$nextTick(()=>{
                    this.$refs["display-box"].scrollTop = this.$refs["display-box"].scrollHeight;
                })
            }
        },
        createContent(remoteUser, nowUser, text) {  // 这个方法是用来将 json的聊天消息数据转换成 html的。
            let html
            // 当前用户消息
            if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
                html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
                    "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
                    "    <div class=\"tip left\">" + text + "</div>\n" +
                    "  </div>\n" +
                    "  <div class=\"el-col el-col-2\">\n" +
                    "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
                    "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
                    "  </span>\n" +
                    "  </div>\n" +
                    "</div>";
            } else if (remoteUser) {   // remoteUser表示远程用户聊天消息，蓝色的气泡
                html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
                    "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
                    "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
                    "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
                    "  </span>\n" +
                    "  </div>\n" +
                    "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
                    "    <div class=\"tip right\">" + text + "</div>\n" +
                    "  </div>\n" +
                    "</div>";
            }
            console.log(html)
            this.content += html;
        },
        messagesPush(data) {
            let oldScroll = this.$refs["display-box"].scrollTop;
            this.messags.push(data);
            console.log(data);
            this.$nextTick(() => {
                console.log(oldScroll)
                console.log(this.$refs["display-box"].scrollTop)
                console.log(this.$refs["display-box"].scrollHeight)
                if (oldScroll + 50 === this.$refs["display-box"].scrollHeight) {
                    this.$refs["display-box"].scrollTop = this.$refs["display-box"].scrollHeight;
                }
            })
        },
        init() {
            // this.user = localStorage.getItem("token") ? JSON.parse(localStorage.getItem("user")) : {}
            let username = this.account;
            let _this = this;
            if (typeof (WebSocket) == "undefined") {
                console.log("您的浏览器不支持WebSocket");
            } else {
                console.log("您的浏览器支持WebSocket");
                let socketUrl = "ws://localhost:8085/websocket/" + username;
                console.log(socketUrl)
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
                
                // 开启一个websocket服务
                socket = new WebSocket(socketUrl);
                //打开事件
                socket.onopen = function () {
                    console.log("websocket已打开");
                };
                //  浏览器端收消息，获得从服务端发送过来的文本消息
                socket.onmessage = function (msg) {
                    console.log("收到数据====" + msg.data)
                    let data = JSON.parse(msg.data)  // 对收到的json数据进行解析， 类似这样的： {"users": [{"username": "zhang"},{ "username": "admin"}]}
                    // console.log(data.content)
                   
                    if (data.users) {  // 获取在线人员信息
                        _this.users = data.users.filter(user => user.username !== username)  // 获取当前连接的所有用户信息，并且排除自身，自己不会出现在自己的聊天列表里
                    } else {
                        // 如果服务器端发送过来的json数据 不包含 users 这个key，那么发送过来的就是聊天文本json数据
                        //  // {"from": "zhang", "text": "hello"}
                        if (data.fromId === _this.toUser) {
                            // _this.messages.push(data)
                            // 构建消息内容
                            // _this.createContent(data.from, null, data.text)
                            _this.messagesPush(data);
                        }
                        
                        // _this.messagesPush(data);
                    }
                };
                //关闭事件
                socket.onclose = function () {
                    console.log("websocket已关闭");
                };
                //发生了错误事件
                socket.onerror = function () {
                    console.log("websocket发生了错误");
                }
            }
        },
    },
    // beforeDestroy() {
    //     socket.onclose = function () {
    //         console.log("websocket已关闭");
    //     };
    // }
}
</script>

<style scoped>
* {
    box-sizing: border-box;
}

.main {
    margin-top: 50px;
    /*background-color: rgb(228, 228, 228);*/
    /*height: 100%;*/
    /*overflow: auto;*/
    /*background-image: linear-gradient(120deg, #155799, #159957);*/
    height: 340px;
    display: flex;
    justify-content: center;
    /*align-items: center;*/
}

.chat-box {
    
    width: 90%;
    min-width: 540px;
    max-width: 2560px;
    /*background-image: linear-gradient(120deg, #155799, #159957);*/
    position: relative;
}

.info-box {
    margin-top: 5px;
}

.chat {
    /*width: 90%;*/
    width: 800px;
    height: 640px;
    /*padding: 5px;*/
    position: absolute;
    left: 0;
    right: 0;
    margin: 5px auto;
    
}
.content-box {
    height: 60%;
    min-height: 300px;
    border: 1px solid grey;
    /*background: #00aaaa;*/
    white-space: pre-wrap;
    overflow: auto;
}
::-webkit-scrollbar {
    width: 0;
    height: 9px;
}

.replay-box {
    /*margin-bottom: -300px;*/
    width: 100%;
    height: 135px;
    margin-top: 0;
    border: 1px solid gray;
    border-top: 0;
    /*background: #00aaaa;*/
}

.reply-content-box{
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