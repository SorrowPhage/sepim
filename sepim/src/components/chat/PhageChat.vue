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
                    <div v-for="(m,index) in messages" :key="index">
                        <ChatLineRight v-if="m.fromId===account" :avatar-url="m.user.avatarUrl" :content="m.content" :type="m.type" :time="formatDate(m.sendTime)"></ChatLineRight>
                        <chat-line v-else :avatarUrl="m.user.avatarUrl" :content="m.content" :type="m.type" :time="formatDate(m.sendTime)"></chat-line>
                    </div>
                </div>
                <div v-show="isLast" class="last-messages">您有新消息</div>
                <div class="replay-box">
                    <div class="reply-content-box">
                        <VueEmoji ref="chat_text" class="emoji-textarea" :value="contentReply" @input="onInput" width="100%" height="80"></VueEmoji>
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
import VueEmoji from "emoji-vue";
import ChatLine from "@/components/chat/Chat-Line";
import ChatLineRight from "@/components/chat/ChatLineRight";
import {mapState} from "vuex";

let socket;
export default {
    name: "PhageChat",
    components: {
        ChatLine,ChatLineRight,VueEmoji
    },
    computed: {
        ...mapState("User", ['account', 'avatarUrl']),
        ...mapState("ChatMessage", ['messages'])
    },
    data() {
        return {
            username: '',
            toUser: this.$route.params.account,
            contentReply:'',
            isLast: false,
        }
    },
    created() {
        this.$store.dispatch("ChatMessage/getChatMessageList", {s1: this.$route.params.account, s2: this.account});
        this.username = this.$route.params.username;
        this.init();
        this.$nextTick(()=>{
            this.$refs["display-box"].scrollTop = this.$refs["display-box"].scrollHeight;
        })
    },
    methods: {
        onInput(event) {
            this.contentReply = event.data;
        },
        formatDate(sendTime) {
            let a = new Date(sendTime).getTime();
            const date = new Date(a);
            const Y = date.getFullYear() + '/';
            const M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '/';
            const D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + '  ';
            const h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
            const m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
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
                this.messages.push({
                    fromId: this.account,
                    toId: this.$route.params.account,
                    content: this.contentReply,
                    user:{avatarUrl: this.avatarUrl}
                });
                this.$refs.chat_text.clear();
                this.contentReply = '';
                this.$nextTick(()=>{
                    this.$refs["display-box"].scrollTop = this.$refs["display-box"].scrollHeight + 50;
                })
            }
        },
        
        messagesPush(data) {
            let oldScroll = this.$refs["display-box"].scrollTop;
            let divHeight = this.$refs["display-box"].clientHeight;
            this.messages.push(data);
            this.$nextTick(() => {
                if (oldScroll + divHeight + 50 >= this.$refs["display-box"].scrollHeight) {
                    this.$refs["display-box"].scrollTop = this.$refs["display-box"].scrollHeight + 50;
                } else {
                    console.log("你有新消息")
                }
            })
        },
        init() {
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
                   
                    if (data.users) {  // 获取在线人员信息
                        _this.users = data.users.filter(user => user.username !== username)  // 获取当前连接的所有用户信息，并且排除自身，自己不会出现在自己的聊天列表里
                    } else {
                        // 如果服务器端发送过来的json数据 不包含 users 这个key，那么发送过来的就是聊天文本json数据
                        if (data.fromId === _this.toUser) {
                            _this.messagesPush(data);
                        }
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
}
</script>

<style scoped>
.emoji-textarea>>>.emoji-vue-textarea{
    outline: none;
}
* {
    box-sizing: border-box;
}

.main {
    margin-top: 50px;
    height: 340px;
    display: flex;
    justify-content: center;
}

.chat-box {
    width: 90%;
    min-width: 540px;
    max-width: 2560px;
    position: relative;
}

.info-box {
    margin-top: 5px;
}

.chat {
    width: 800px;
    height: 640px;
    position: absolute;
    left: 0;
    right: 0;
    margin: 5px auto;
    
}
.content-box {
    height: 60%;
    min-height: 300px;
    border: 1px solid grey;
    white-space: pre-wrap;
    overflow: auto;
}
::-webkit-scrollbar {
    width: 0;
    height: 9px;
}

.replay-box {
    width: 100%;
    height: 135px;
    margin-top: 0;
    border: 1px solid gray;
    border-top: 0;
}

.reply-content-box{

}
.sp-textarea-comment {
    display: block;
    width: 100%;
    padding: 4px 12px;
    resize: none;
    font-size: 14px;
    line-height: 22px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    -webkit-transition: height .3s ease-in-out;
    transition: height .3s ease-in-out;
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
.last-messages {
    background: #00aaaa;
    height: 25px;
}
</style>