<template>
    <div class="main">
        <div v-show="players">
            <div>{{hostname}}</div>
            <div>
                <h2 class="h2Title">五子棋</h2>
                <h3>{{victory}}</h3>
                <div class="Fbuttons">
<!--                    <input type="button" value="重新开始" class="restart" @click="restartInit()" />-->
                    <input type="button" value="悔棋" class="regret" @click="regret()" />
<!--                    <input type="button" value="撤销悔棋" class="undo" @click="undo()" />-->
<!--                    <input type="button" :value="toggle?'切换dom':'切换canvas'" class="toggle" @click="toggleF()" />-->
                </div>
                <div class="wuziqi-main">
                    <canvas v-show="toggle" id="myCanvas" ref="canvas" width="480" height="480">当前浏览器不支持Canvas</canvas>
                    <div v-show="!toggle" id="chess" ref="chessBox">
                        <!-- <div id="box01"></div>
                        <div id="box02"></div> -->
                    </div>
                </div>
            </div>
            <div>
                {{opponent}}
            </div>
        </div>
        <div v-show="!players">
            <div v-show="!cpb">
                <el-button type="primary" @click="compatible()">匹配</el-button>
            </div>
            <div v-show="cpb">
                <div class="loading">
                </div>
                <div class="time">
                    {{compatibleTime}}
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapState} from "vuex";

let socket;
export default {
    name: "FiveInRow",
    ...mapState("User", ['account', 'avatarUrl']),
    data() {
        return{
            compatibleTime: 0,
            cpb: false,
            intervalId: null,
            players: false,
            hostname: 'user',
            opponent: 'sp2645354',
            pieceMapArr: [], //记录棋盘落子情况
            pieceColor: ["black", "white"], //棋子颜色
            step: 0, //记录当前步数
            checkMode: [ //输赢检查方向模式
                [1, 0], //水平
                [0, 1], //竖直
                [1, 1], //左斜线
                [1, -1], //右斜线
            ],
            flag: false,
            victory: '',
            history: [], //历史记录位置
            historyVal: [], //历史记录不被删除数组
            stepHistory: 0,
            domPiece:[], //
            toggle: true //true为canvas,false为dom
        }
    },
    mounted() {
        this.init();
        
        const myCanvas = document.getElementById("myCanvas");
        if (!myCanvas.getContext) {
            alert("当前浏览器不支持Canvas.");
            this.toggle = false;
            this.drawpieceBoardDom();
        } else {
            console.log("当前浏览器支持Canvas", this.toggle)
            this.drawpieceBoard();
            const canvas = this.$refs.canvas;
            // 添加点击监听事件
            canvas.addEventListener("click", e => {
                if (this.flag) {
                    alert("游戏结束,请重新开始~");
                    return;
                }
                
                //判断点击范围是否越出棋盘
                if (e.offsetX < 25 || e.offsetX > 450 || e.offsetY < 25 || e.offsetY > 450) {
                    return;
                }
                let dx = Math.floor((e.offsetX + 15) / 30) * 30;
                let dy = Math.floor((e.offsetY + 15) / 30) * 30;
                
                if (this.pieceMapArr[dx / 30 - 1][dy / 30 - 1] === 0) {
                    let chess;
                    
                    if (this.history[0] === undefined) {
                        if (this.hostname === this.$store.state.User.account) {
                            chess = {
                                fromId: this.$store.state.User.account,
                                toId: this.opponent,
                                dx: dx,
                                dy: dy,
                                color: "black",
                            };
                        }
                    } else {
                        if (this.hostname === this.$store.state.User.account && this.history[this.history.length - 1].color === "white") {
                            chess = {
                                fromId: this.$store.state.User.account,
                                toId: this.opponent,
                                dx: dx,
                                dy: dy,
                                color: "black",
                            };
                        } else if (this.opponent === this.$store.state.User.account && this.history[this.history.length - 1].color === "black") {
                            chess = {
                                fromId: this.$store.state.User.account,
                                toId: this.hostname,
                                dx: dx,
                                dy: dy,
                                color: "white",
                            };
                        } else {
                            return;
                        }
                    }
                    //历史记录位置
                    // this.history.length = this.step;
                    this.drawPiece(chess.dx, chess.dy, chess.color); //落下棋子
                    this.pieceMapArr[chess.dx / 30 - 1][chess.dy / 30 - 1] = chess.color;
                    
                    this.history.push(chess);
                    this.historyVal.push(chess);
                    socket.send(JSON.stringify(chess));
                    
                    for (let i = 0; i < 4; i++) {
                        this.checkWin(dx / 30 - 1, dy / 30 - 1, chess.color, this.checkMode[i]);
                    }
                    this.step++;
                } else {
                    this.$message({
                        showClose: true,
                        message: '不能落在有棋子的地方',
                        type: 'error',
                        center: true,
                    });
                }
            });
        }
        
    },
    beforeDestroy() {
        socket.close();
    },
    methods: {
        add() {
            this.intervalId = setInterval(() => {
                this.compatibleTime += 1
            }, 1000)
        },
        // 停止定时器
        stop() {
            clearInterval(this.intervalId)//清除计时器
            this.intervalId = null;//设置为null
        },
        compatible() {
            this.add();
            this.cpb = true;
            socket.send(JSON.stringify({cpb:true,formId:this.$store.state.User.account}))
        },
        chessmanPush(data) {
            this.drawPiece(data.dx, data.dy, data.color); //落下棋子
            this.history.push(data);
            this.historyVal.push(data)
            this.pieceMapArr[data.dx / 30 - 1][data.dy / 30 - 1] = data.color;
            for (let i = 0; i < 4; i++) {
                this.checkWin(data.dx / 30 - 1, data.dy / 30 - 1, data.color, this.checkMode[i]);
            }
            this.step++;
        },
        init() {
            // this.user = localStorage.getItem("token") ? JSON.parse(localStorage.getItem("user")) : {}
            let username = this.$store.state.User.account;
            let _this = this;
            if (typeof (WebSocket) == "undefined") {
                console.log("您的浏览器不支持WebSocket");
            } else {
                console.log("您的浏览器支持WebSocket");
                let socketUrl = "ws://localhost:8085/gobang/" + username;

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
                        _this.users = data.users.filter(user => user.username !== username);  // 获取当前连接的所有用户信息，并且排除自身，自己不会出现在自己的聊天列表里
                        console.log(_this.users)
                    } else {
                        if (data.fromId === _this.hostname || data.toId === _this.hostname) {
                            console.log("+++++++++++++++++++++")
                            _this.chessmanPush(data);
                        }
                        if (data.hostname != null || data.hostname !== undefined) {
                            _this.hostname = data.hostname;
                            _this.opponent = data.opponent;
                            _this.players = this;
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
        
        toggleF() {
            this.toggle = !this.toggle;
            if (!this.toggle) {
                // console.log("当前---------------1")
                // let elem = document.getElementById('box01');
                // if (elem !== null) {
                // 	elem.parentNode.removeChild(elem);
                // 	let elem02 = document.getElementById('box02');
                // 	elem02.parentNode.removeChild(elem02);
                // }
                // this.drawpieceBoardDom();
                this.restartInit()
            } else {
                this.restartInit()
                // this.drawpieceBoard();
            }
        },
        //初始化棋盘数组
        pieceArr() {
            for (let i = 0; i < 15; i++) {
                this.pieceMapArr[i] = [];
                for (let j = 0; j < 15; j++) {
                    this.pieceMapArr[i][j] = 0;
                }
            }
        },
        //重新开始
        restartInit() {
            if (!this.toggle) {
                // console.log("-----dom-------")
                var elem = document.querySelector('#box01');
                // console.log("elem",elem)
                if (elem != null ) {
                    elem.parentNode.removeChild(elem);
                    let elem02 = document.querySelector('#box02');
                    elem02.parentNode.removeChild(elem02);
                }
                this.drawpieceBoardDom();
                this.flag = false;
                this.step = 0;
                this.stepHistory = 0;
                this.historyVal = [];
                this.history = [];
            } else {
                //重画
                this.repaint();
                // 绘制棋盘
                this.drawpieceBoard();
                this.flag = false;
                this.step = 0;
                this.stepHistory = 0;
                this.historyVal = [];
                this.history = [];
            }
        },
        //---------canvas----------
        // 绘制棋盘
        drawpieceBoard() {
            //初始化棋盘数组
            this.pieceArr();
            //canvas 绘制
            let canvas = this.$refs.canvas
            // 调用canvas元素的getContext 方法访问获取2d渲染的上下文
            let context = canvas.getContext("2d");
            context.strokeStyle = '#666'
            for (let i = 0; i < 15; i++) {
                //落在方格(canvas 的宽高是450)
                // context.moveTo(15 + i * 30, 15)
                // context.lineTo(15 + i * 30, 435)
                // context.stroke()
                // context.moveTo(15, 15 + i * 30)
                // context.lineTo(435, 15 + i * 30)
                // context.stroke()
                //落在交叉点(480)
                context.beginPath();
                context.moveTo((i + 1) * 30, 30);
                context.lineTo((i + 1) * 30, canvas.height - 30);
                context.closePath();
                context.stroke();
                context.beginPath();
                context.moveTo(30, (i + 1) * 30);
                context.lineTo(canvas.width - 30, (i + 1) * 30);
                context.closePath();
                context.stroke();
            }
        },
        //绘制棋子
        drawPiece(x, y, color) {
            let canvas = this.$refs.canvas
            let context = canvas.getContext("2d");
            context.beginPath(); //开始一条路径或重置当前的路径
            context.arc(x, y, 15, 0, Math.PI * 2, false);
            context.closePath();
            context.fillStyle = color;
            context.fill();
        },
        //胜负判断函数
        checkWin(x, y, color, mode) {
            let count = 1; //记录
            for (let i = 1; i < 5; i++) {
                if (this.pieceMapArr[x + i * mode[0]]) {
                    if (this.pieceMapArr[x + i * mode[0]][y + i * mode[1]] === color) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
            for (let j = 1; j < 5; j++) {
                if (this.pieceMapArr[x - j * mode[0]]) {
                    if (this.pieceMapArr[x - j * mode[0]][y - j * mode[1]] === color) {
                        count++;
                    } else {
                        break;
                    }
                }
            }
            // console.log('胜负判断函数', count)
            // console.log('color', color)
            if (count >= 5) {
                if (color === 'black') {
                    this.victory = "黑子棋方胜利！";
                } else {
                    this.victory = "白子棋方胜利！";
                }
                // 游戏结束
                // console.log('游戏结束')
                this.flag = true;
            }
        },
        //重画函数
        repaint() {
            //重画
            let canvas = this.$refs.canvas;
            let context = canvas.getContext("2d");
            context.fillStyle = "bisque";
            context.fillRect(0, 0, canvas.width, canvas.height);
            context.beginPath();
            context.closePath();
        },
        
        //悔棋:
        // canvas 创建一个二维数组，下棋或者悔棋都操作这个数组。操作完数据，把画布全清，重新用数据画一个棋盘。
        // dom   二维数组删除数组最后一项, 先清空棋子的填充颜色,在渲染上颜色
        regret() {
            if (!this.toggle) {
                // console.log("-----dom------this.domPiece",this.domPiece)
                if (this.history.length && !this.flag) {
                    this.history.pop(); //删除数组最后一项
                    console.log("-----dom------this.history", this.history)
                    //重画
                    this.pieceArr();
                    // let elem = document.getElementById('box01');
                    // if (elem !== null) {
                    // 	elem.parentNode.removeChild(elem);
                    // 	let elem02 = document.getElementById('box02');
                    // 	elem02.parentNode.removeChild(elem02);
                    // } //这个太耗性能了
                    // this.drawpieceBoardDom();
                    // 清空棋子的填充颜色
                    this.domPiece.forEach(e => {
                        e.forEach(qz => {
                            qz.style.backgroundColor = '';
                        })
                    });
                    // 渲染棋子颜色
                    this.history.forEach(e => {
                        this.domPiece[e.m][e.n].style.backgroundColor = e.color
                        this.pieceMapArr[e.m][e.n] = e.color;
                    });
                    this.step--
                } else {
                    this.$message({
                        showClose: true,
                        message: '不能悔棋了',
                        type: 'error',
                        center: true,
                    });
                }
                
            } else {
                if (this.history.length && !this.flag) {
                    this.history.pop(); //删除数组最后一项
                    //重画
                    this.repaint();
                    // 绘制棋盘
                    this.drawpieceBoard();
                    //绘制棋子
                    this.history.forEach(e => {
                        this.drawPiece(e.dx, e.dy, e.color)
                        this.pieceMapArr[e.dx / 30 - 1][e.dy / 30 - 1] = e.color;
                    });
                    this.step--
                } else {
                    alert("已经不能悔棋了~")
                }
            }
        },
        //撤销悔棋
        undo() {
            if (!this.toggle) {
                // console.log("-----dom------this.domPiece",this.domPiece)
                if ((this.historyVal.length > this.history.length) && !this.flag) {
                    this.history.push(this.historyVal[this.step])
                    console.log("-----dom------this.history", this.history)
                    // 清空棋子的填充颜色
                    this.domPiece.forEach(e => {
                        e.forEach(qz => {
                            qz.style.backgroundColor = '';
                        })
                    });
                    // 渲染棋子颜色
                    this.history.forEach(e => {
                        this.domPiece[e.m][e.n].style.backgroundColor = e.color
                        this.pieceMapArr[e.m][e.n] = e.color;
                    });
                    this.step++
                } else {
                    this.$message({
                        showClose: true,
                        message: '不能撤销悔棋了',
                        type: 'error',
                        center: true,
                    });
                }
                
            } else {
                if ((this.historyVal.length > this.history.length) && !this.flag) {
                    this.history.push(this.historyVal[this.step])
                    //重画
                    this.repaint();
                    // 绘制棋盘
                    this.drawpieceBoard();
                    this.history.forEach(e => {
                        this.drawPiece(e.dx, e.dy, e.color)
                        this.pieceMapArr[e.dx / 30 - 1][e.dy / 30 - 1] = e.color;
                    });
                    this.step++
                } else {
                    this.$message({
                        showClose: true,
                        message: '不能撤销悔棋了',
                        type: 'error',
                        center: true,
                    });
                }
            }
        },
        
        // -----------dom-----------
        drawpieceBoardDom() {
            // console.log("this", this)
            let that = this;
            //调用初始化棋盘数组函数
            that.pieceArr();
            //创建一个容器
            const box = document.querySelector("#chess");
            const box01 = document.createElement("div");
            box01.setAttribute("id", "box01");
            box.appendChild(box01);
            //画棋盘
            const chess01 = document.querySelector("#box01");
            const box02 = document.createElement("div");
            box02.setAttribute("id", "box02");
            box.appendChild(box02);
            let arr = new Array();
            for (let i = 0; i < 14; i++) {
                arr[i] = new Array();
                for (let j = 0; j < 14; j++) {
                    arr[i][j] = document.createElement("div");
                    arr[i][j].setAttribute("class", "squre");
                    box02.appendChild(arr[i][j]);
                }
            }
            //画棋子
            let arr01 = this.domPiece;
            for (let i = 0; i < 15; i++) {
                arr01[i] = new Array();
                for (let j = 0; j < 15; j++) {
                    arr01[i][j] = document.createElement("div");
                    arr01[i][j].setAttribute("class", "qz");
                    chess01.appendChild(arr01[i][j]);
                }
            }
            // console.log("this.domPiece",this.domPiece)
            // 填充颜色和判断
            for (let m = 0; m < 15; m++) {
                for (let n = 0; n < 15; n++) {
                    arr01[m][n].onclick = function() {
                        //判断游戏是否结束
                        if (!that.flag) {
                            if (that.pieceMapArr[m][n] == 0) {
                                //黑白交换下棋
                                // console.log(this);
                                // console.log('落下棋子', that.pieceColor[that.step % 2])
                                //确保填充颜色正确进行了判断
                                if (this.className == "qz" && that.step % 2 == 0 && this.style.backgroundColor == "") {
                                    //下棋填充黑颜色
                                    this.style.backgroundColor = that.pieceColor[that.step % 2];
                                    //写入棋盘数组
                                    that.pieceMapArr[m][n] = that.pieceColor[that.step % 2];
                                    //历史记录位置
                                    that.history.length = that.step;
                                    that.history.push({
                                        m,
                                        n,
                                        color: that.pieceColor[that.step % 2]
                                    });
                                    that.historyVal.push({
                                        m,
                                        n,
                                        color: that.pieceColor[that.step % 2]
                                    });
                                    that.stepHistory++
                                    console.log('this.history', that.history);
                                } else if (this.className == "qz" && that.step % 2 != 0 && this.style.backgroundColor == "") {
                                    //下棋填充白颜色
                                    this.style.backgroundColor = that.pieceColor[that.step % 2];
                                    //写入棋盘数组
                                    that.pieceMapArr[m][n] = that.pieceColor[that.step % 2];
                                    //历史记录位置
                                    that.history.length = that.step;
                                    that.history.push({
                                        m,
                                        n,
                                        color: that.pieceColor[that.step % 2]
                                    });
                                    that.historyVal.push({
                                        m,
                                        n,
                                        color: that.pieceColor[that.step % 2]
                                    });
                                    that.stepHistory++
                                    console.log('this.history', that.history);
                                }
                                //检查当前是否赢了
                                for (var i = 0; i < 4; i++) {
                                    that.checkWin(m, n, that.pieceColor[that.step % 2], that.checkMode[i]);
                                }
                                that.step++;
                                // console.log('that.step', that.step);
                            } else {
                                this.$message({
                                    showClose: true,
                                    message: '不能落在有棋子的地方',
                                    type: 'error',
                                    center: true,
                                });
                                return;
                            }
                        } else {
                            // that.flag = true;
                            this.$message({
                                showClose: true,
                                message: '游戏结束',
                                type: 'success',
                                center: true,
                            });
                            return;
                        }
                    }
                }
            }
            
        },
        
    }
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
body {
    margin: 0;
    padding: 0;
}
#app{
    padding-left: 30%;
    width: 500px;
}
.h2Title{
    text-align: center;
}
#app h3{
    color: red;
}
.Fbuttons{
    margin-bottom: 1rem;
    display: flex;
}
.wuziqi-main{
    background-color: bisque;
    width: 30rem;
}
.restart,.regret,.undo{
    background: bisque;
    padding: 6px 10px;
    border-radius: 6px;
    font-size: 12px;
    cursor: pointer;
}
#chess {
    position: relative;
    width: 440px;
    height: 450px;
    padding-left: 30px;
    padding-top: 30px;
    background-color: bisque;
}

#chess .squre {
    width: 28px;
    height: 28px;
    border: 1px solid #666;
    float: left;
}

#box01 .squre:hover {
    background-color: pink;
}

#box01 {
    position: absolute;
    margin: 0 auto;
    width: 450px;
    height: 450px;
    top: 15px;
    left: 15px;
}

#box01 .qz {
    /* width: 28px;
    height: 28px; */
    width: 30px;
    height: 30px;
    border: 0px solid #C7C7C7;
    float: left;
    border-radius: 50%;
    /* margin: 1px; */
}

#box01 .qz:hover {
    background-color: pink;
}
.toggle{
    float: right;
}
.loading{
    border: 16px solid #f3f3f3;
    border-radius: 50%;
    border-top: 16px solid #02b2c9;
    width: 120px;
    height: 120px;
    -webkit-animation: spin 2s linear infinite;
    animation: spin 2s linear infinite;
}
@-webkit-keyframes spin {
    0% {
        -webkit-transform: rotate(0deg);
    }
    100% {
        -webkit-transform: rotate(360deg);
    }
}
@keyframes spin {
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
}
.time {
    margin-top: 10px;
    text-align: center;
    font-size: 20px;
    font-weight: bold;
    color: #00bfff;
}



</style>