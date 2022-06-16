<template>
    <div class="main">
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <header class="table-heading">Document</header>
                        <div class="pane-body"
                             v-loading="loading"
                             element-loading-text="拼命加载中"
                             element-loading-spinner="el-icon-loading"
                             element-loading-background="rgba(0, 0, 0, 0.8)"
                        >
                            <div class="md-pane">
                                <div class="md-box">
                                    <div class="author-box">
                                        <span class="text-author">
                                            作者：{{ author }}
                                        </span>
                                        <span>
                                            描述:
                                            {{ roughly }}
                                        </span>
                                        <span>
                                            浏览量:
                                            {{ readNum }}
                                        </span>
                                    </div>
                                    <div ref="md_show"  class="markdown-body" v-html="content"></div>
                                    <PhageComment :userAvatarUrl="avatar_url" :comments="comments"></PhageComment>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapGetters, mapState} from "vuex";
import 'mavon-editor/dist/markdown/github-markdown.min.css';
import 'mavon-editor/dist/highlightjs/styles/github.min.css';
import {Message} from 'element-ui';
import axios from "axios";
import PhageComment from "@/components/depository/list/comment/PhageComment";

export default {
    name: "ReadMd",
    // eslint-disable-next-line vue/no-unused-components
    components:{PhageComment},
    data() {
        return {
            author: '',
            roughly: "",
            readNum: '',
            content: '',
            activeName: 'tab0',
            tabPosition: 'right',
            scroll: '',
            navList: [],
            substance:'',
            isShow: false,
            loading: true,
        };
    },
    computed: {
        ...mapGetters("User", ["avatar_url"]),
        ...mapState("User", ["userName"]),
        ...mapState("Comment", ['comments'])
    },
    methods: {
        handleClick(tab, event) {
            this.jump(tab.index)
        },
        dataScroll: function () {
            this.scroll = document.documentElement.scrollTop || document.body.scrollTop;
        },
        jump(index) {
            let jump = document.querySelectorAll("h1,h2,h3,h4,h5,h6");
            // 获取需要滚动的距离
            let total = jump[index].offsetTop - 80;
            // Chrome
            document.body.scrollTop = total;
            // Firefox
            document.documentElement.scrollTop = total;
            // Safari
            window.pageYOffset = total;
            // $('html, body').animate({
            // 'scrollTop': total
            // }, 400);
        },
        loadScroll: function () {
            let self = this;
            let navs = document.querySelectorAll('.el-tabs__item');
            // var sections = document.getElementsByClassName('section');
            for (var i = self.navList.length - 1; i >= 0; i--) {
                if (self.scroll >= self.navList[i].offsetTop - 120) {
                    self.activeName = 'tab' + i
                    break;
                }
            }
        },
        selectAllTitle() {
            let title = document.querySelectorAll("h1,h2,h3,h4,h5,h6");
            this.navList = Array.from(title);
            this.navList.forEach(item => {
                item.name = item.innerHTML
            })
            this.navList.forEach(el => {
                let index = el.localName.indexOf('h');
                el.lev = 'lev' + el.localName.substring(index + 1, el.localName.length)
            });
        },
        dragAround(anyElement) {
            let mouseDown = false; // 鼠标是否按下
            let x = 0; // 鼠标点击的下标
            let left = 0; // 当前滚动条位置
            anyElement.onmousedown = function (e) {
                mouseDown = true;       // 鼠标按下
                x = e.clientX;          // 获取鼠标点击位置 （x坐标）
                left = this.scrollLeft; // 滚动条当前位置
            };
            anyElement.onmousemove = function (e) {
                // 鼠标按下
                if (mouseDown) {
                    let curX = e.clientX; // 鼠标移动到当前的位置
                    let diffX = curX - x; // 鼠标移动距离（当前位置 减去之前的位置）
                    this.scrollLeft = left - diffX;
                }
            };
            anyElement.onmouseup = function () {
                mouseDown = false;
            };
            anyElement.onmouseleave = function () {
                mouseDown = false;
            };
        },
        
    },
    watch: {
        scroll: function () {
            this.loadScroll()
        }
    },
    mounted() {
        axios.get("http://localhost:8080/api/md/read", {params: {id: this.$route.query.id}}).then(res => {
            if (res.data.flag === "md_read_succeed") {
                this.author = res.data.data.username;
                this.roughly = res.data.data.roughly;
                this.readNum = res.data.data.readNum;
                this.content = res.data.data.content;
                this.$store.commit("Comment/GET_COMMENTS", res.data.data.comments);
                this.loading = false;
                this.$nextTick(() => {
                    // scroll代表滚动条距离页面顶部距离
                    window.addEventListener('scroll', this.dataScroll);
                    this.selectAllTitle();
                    setTimeout(() => {
                        let navs = document.querySelectorAll('.el-tabs__item');
                        for (let i = navs.length - 1; i >= 0; i--) {
                            document.querySelector('#' + navs[i].id).style.padding = "0";
                            if (this.navList[i].lev === 'lev1') {
                                document.querySelector('#' + navs[i].id).style.paddingLeft = "5px";
                            } else if (this.navList[i].lev === 'lev2') {
                                document.querySelector('#' + navs[i].id).style.paddingLeft = "10px";
                            } else if (this.navList[i].lev === 'lev3') {
                                document.querySelector('#' + navs[i].id).style.paddingLeft = "15px";
                            } else if (this.navList[i].lev === 'lev4') {
                                document.querySelector('#' + navs[i].id).style.paddingLeft = "20px";
                                document.querySelector('#' + navs[i].id).style.fontWeight = "400";
                            } else if (this.navList[i].lev === 'lev5') {
                                document.querySelector('#' + navs[i].id).style.paddingLeft = "25px";
                                document.querySelector('#' + navs[i].id).style.fontWeight = "400";
                            }
                        }
                    });
                    
                    const code = document.querySelectorAll('pre code');
                    code.forEach((item) => {
                        // 取出 code 的父元素 pre（后面方便使用）
                        let pre = item.parentElement;
                        
                        // 添加拖动属性
                        this.dragAround(item);
                        
                        // 新建元素 代码块序号
                        let lineNumBox = document.createElement('div');
                        lineNumBox.setAttribute('style', 'height: ' + item.offsetHeight + 'px');
                        lineNumBox.className = 'line-num-box';
                        // 插入序号 计算方式：获取code元素的高并除以行高，得到行数，根据行数插入序号
                        let num = '';  // 设行高二十
                        for (let i = 1; i <= Math.ceil(item.offsetHeight / 20); i++) {
                            num += i + '\n'; // 序号加上换行符
                        }
                        lineNumBox.innerText = num;// 插入序号
                        item.parentElement.insertBefore(lineNumBox, item);
                        
                        let codeBox = document.createElement('div');
                        codeBox.className = 'code-box';
                        codeBox.appendChild(item);
                        
                        pre.appendChild(codeBox);
                        
                        let lang = pre.lastElementChild.firstElementChild.className;
                        let icon = `<div class="mac-icon">` +
                            `<span class="mac-icon-red"></span>` +
                            `<span class="mac-icon-yellow"></span>` +
                            `<span class="mac-icon-green"></span>` +
                            `<span class="mac-icon-lang">${lang.split('-')[1].toUpperCase()}</span>` +
                            `<button class="copy-button">复制</button>` +
                            `<button class="full-screen-button">全屏</button>` +
                            `</div>`;
                        pre.insertAdjacentHTML('afterbegin', icon);
                        
                        let copyButton = pre.firstElementChild.getElementsByClassName('copy-button')[0];
                        copyButton.onclick = function () {
                            const copyPromise = navigator.clipboard.writeText(pre.lastElementChild.innerText);
                            copyPromise.then(() => {
                                Message({
                                    message: '复制成功',
                                    showClose: true,
                                    type: 'success',
                                    center: true,
                                });
                            }).catch(() => {
                                Message({
                                    message: '复制失败',
                                    showClose: true,
                                    type: 'error',
                                    center: true,
                                });
                            });
                        };
                        // 获取全屏按钮元素
                        let fullScreenButton = pre.firstElementChild.getElementsByClassName('full-screen-button')[0];
                        fullScreenButton.onclick = function () {
                            // 此写法基于 pre 元素没有其他任何类名的情况下
                            if (pre.className === 'pre-full-screen') {
                                this.innerText = '全屏';
                                pre.className = '';
                                pre.setAttribute("title", "");
                            } else {
                                this.innerText = '关闭';
                                pre.className = 'pre-full-screen';
                                pre.setAttribute("title", "双击关闭全屏");
                            }
                        };
                        // 双击关闭全屏
                        pre.ondblclick = function () {
                            fullScreenButton.innerText = '全屏';
                            // 此写法基于 pre 元素没有其他任何类名的情况下
                            this.className = '';
                            pre.setAttribute("title", "");
                        };
                        // hljs.highlightBlock(codeBox.firstElementChild);
                    });
                });
            }
        })
        
        
    }
}
</script>

<style scoped>

* {
    box-sizing: border-box;
}

.main {
    margin-top: 50px;
    background-color: rgb(228, 228, 228);
    height: 100%;
    overflow: auto;
}

.page-heading {
    width: 100%;
    padding: 15px;
    position: relative;
}

.page-heading h3 {
    display: block;
    font-size: 1.17em;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0;
    margin-inline-end: 0;
    font-weight: bold;
}

.wrapper {
    padding: 15px;
}

.row {
    margin-left: -15px;
    margin-right: -15px;
}

.col {
    position: relative;
    min-height: 1px;
    padding-left: 15px;
    padding-right: 15px;
    width: 100%;
}

.panel {
    margin-bottom: 20px;
    background-color: white;
    border: 1px solid transparent;
    border-radius: 4px;
}

.table-heading {
    border-bottom: 1px dotted rgba(0, 0, 0, 0.2);
    padding: 15px;
    text-transform: uppercase;
    color: #535351;
    font-size: 14px;
    font-weight: bold;
}

.pane-body {
    padding: 15px;
    background: #f9f9f9;
}

.author-box {
    width: 100%;
    padding: 15px;
    border-bottom: 1px solid #9f0936;
}

.md-pane {
    width: 100%;
    /*background-color: #0aaeb3;*/
    display: flex;
    justify-content: center;
}

.md-box {
    width: 50%;
    min-width: 900px;
    font-size: 17px;
}

.text-author {
    font-weight: bold;
}

.author-pane {
    width: 15%;
    margin-right: 5px;
}

.catalogue-pane {
    width: 15%;
    margin-left: 5px;
}

.catalogue-fixed {
    position: fixed;
    right: 15px;
    background-color: white;
    width: 320px;
}

.is-right {
    float: left;
}

>>> pre {
    /*border: 1px solid #282c34;*/
    border: 1px solid white;
    background-color: white !important;
    /*border-radius: 15px !important;*/
}

/* 自定义全屏样式 */
>>> .pre-full-screen {
    position: fixed;
    left: 0 !important;
    top: 0 !important;
    width: 100vw !important;
    z-index: 100;
    height: 100vh !important;
    background-color: white !important;
    /*background-color: #282c34 !important;*/
    overflow: auto;
}

>>> .line-num-box {
    display: inline-block;
    color: #398bff;
    border-right: 2px solid #398bff;
    line-height: 20px !important;
    font-size: 16px !important;
    text-align: right;
    padding-left: 10px;
    padding-right: 10px;
}

>>> .code-box {
    display: inline-block;
    vertical-align: top;
    width: calc(100% - 50px);
    border-left-style: none;
}

/*滚动条样式 https://m.php.cn/article/475268.html*/
>>> code {
    line-height: 20px !important;
    font-size: 16px !important;
    vertical-align: top;
    padding-top: 0 !important;
    padding-bottom: 0 !important;
    padding-left: 10px !important;
}

>>> code::-webkit-scrollbar { /*滚动条整体样式*/
    /*width: 100px !important;*/
    height: 10px;
    border-radius: 5px;
    background-color: darkgray;
}

>>> code::-webkit-scrollbar-thumb { /*滚动条里面小方块*/
    border-radius: 5px;
    background-color: dimgrey;
}

/*>>> code::-webkit-scrollbar-button{!*滚动条的轨道的两端按钮，允许通过点击微调小方块的位置*!*/
/*    !*border-radius: 5px;*!*/
/*    background-color: yellow;*/
/*}*/

>>> .mac-icon {
    border-bottom: 1px solid silver;
    margin-bottom: 5px;
    color: deeppink;
}

>>> .mac-icon > span {
    display: inline-block;
    letter-spacing: 5px;
    word-spacing: 5px;
    width: 16px;
    height: 16px;
    border-radius: 8px;
}

>>> .mac-icon-red {
    background-color: red;
}

>>> .mac-icon-yellow {
    margin-left: 10px;
    background-color: yellow;
}

>>> .mac-icon-green {
    margin-left: 10px;
    background-color: green;
}

>>> .mac-icon-lang {
    width: 50px !important;
    padding-left: 10px;
    font-size: 16px;
    vertical-align: top;
}

>>> .copy-button, >>> .full-screen-button {
    width: 40px;
    height: 20px;
    background-color: #398bff;
    margin-bottom: 3px;
    border-radius: 5px;
    outline: none;
    border: none;
}

>>> .full-screen-button {
    width: 40px !important;
    height: 20px;
}

>>> .copy-button {
    /*
        减去padding 2*16（如果使box-sizing: border-box;则不用减去)，
        减去图标 3*16
        图标间隙 10*2
        语言 宽度 50px
        减去本身宽 40px
        全屏按钮 margin-left 10px
        全屏按钮宽 40px
    */
    margin-left: calc(100% - 208px);
}

>>> .full-screen-button {
    margin-left: 10px;
}

>>> .copy-button:hover, >>> .full-screen-button:hover {
    background-color: dimgrey;
}

</style>
