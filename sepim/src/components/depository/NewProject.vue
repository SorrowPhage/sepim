<template>
    <div class="main">
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
<!--                        <header class="table-heading">新建文件</header>-->
                        <div class="pane-body">
                            <div class="sp-operation-bar">
                                <input v-model="title"  class="sp-inout-title" type="text" placeholder="标题"/>
                                <input v-model="roughly"  class="sp-inout-roughly" type="text" placeholder="描述"/>
                                <el-select class="sp-space" v-model="value" placeholder="类型" >
                                    <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value"
                                    >
                                    </el-option>
                                </el-select>
                                <el-button class="sp-space" type="primary" @click="save" plain>创建</el-button>
                                <UploadFile></UploadFile>
                            </div>
                            <div class="sp-mavon">
                                <mavon-editor
                                    v-model="md"
                                    :placeholder="'Edit ···'"
                                    ref="md"
                                    @imgAdd="renderImgAdd"
                                    @imgDel="renderImgDel"
                                    @change="change"
                                    @save="save"
                                    :toolbarsBackground="'#afb8c1'"
                                    :externalLink="externalLink"
                                    style="height: calc(100vh - 50px)"
                                    :toolbars="toolbars"
                                />
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import axios from "axios";
import UploadFile from "@/components/depository/upload/UploadFile";
export default {
    name: "NewProject",
    components:{UploadFile},
    data() {
        return {
            options: [{
                value: '私有的',
                label: '私有的'
            }, {
                value: '公开的',
                label: '公开的'
            }],
            value: '私有的',
            md: '',
            title: '',
            roughly:'',
            externalLink: {
                markdown_css: function () {
                    // 这是你的markdown css文件路径
                    return "/mavon-editor/markdown/github-markdown.min.css";
                },
                hljs_js: function () {
                    // 这是你的hljs文件路径
                    return "/mavon-editor/highlightjs/highlight.min.js";
                },
                hljs_css: function (css) {
                    // 这是你的代码高亮配色文件路径
                    return "/mavon-editor/highlightjs/styles/" + css + ".min.css";
                },
                hljs_lang: function (lang) {
                    // 这是你的代码高亮语言解析路径
                    return "/mavon-editor/highlightjs/languages/" + lang + ".min.js";
                },
                katex_css: function () {
                    // 这是你的katex配色方案路径路径
                    return "/mavon-editor/katex/katex.min.css";
                },
                katex_js: function () {
                    // 这是你的katex.js路径
                    return "/mavon-editor/katex/katex.min.js";
                },
            },
            toolbars: {
                bold: true, // 粗体
                italic: true, // 斜体
                header: true, // 标题
                underline: true, // 下划线
                strikethrough: true, // 中划线
                mark: true, // 标记
                superscript: true, // 上角标
                subscript: true, // 下角标
                quote: true, // 引用
                ol: true, // 有序列表
                ul: true, // 无序列表
                link: true, // 链接
                imagelink: true, // 图片链接
                code: true, // code
                table: true, // 表格
                fullscreen: false, // 全屏编辑
                readmodel: true, // 沉浸式阅读
                htmlcode: true, // 展示html源码
                help: true, // 帮助
                /* 1.3.5 */
                undo: true, // 上一步
                redo: true, // 下一步
                trash: true, // 清空
                save: true, // 保存（触发events中的save事件）
                /* 1.4.2 */
                navigation: true, // 导航目录
                /* 2.1.8 */
                alignleft: true, // 左对齐
                aligncenter: true, // 居中
                alignright: true, // 右对齐
                /* 2.2.1 */
                subfield: true, // 单双栏模式
                preview: true, // 预览
            }
    
        };
    },
    methods:{
        renderImgAdd (pos, $file) {
            axios.post("http://localhost:8080/api/md/upload",{file:$file},
                {headers: {'Content-Type': 'multipart/form-data'}}).then(res=>{
                this.$refs.md.$img2Url(pos, res.data.data);
            })
        },
        renderImgDel (pos) {
            axios.get("http://localhost:8080/api/md/delete", {params: {url: pos[0]}});
        },
        change (value, render) {
            this.mdContent = value;
            this.content = render
        },
        save(){
            if (this.title === "" || this.title === null) {
                this.$message({
                    message: '标题不能为空',
                    showClose: true,
                    type: 'error',
                    center: true,
                });
                return;
            }
            axios.post("http://localhost:8080/api/md/save",{title:this.title, roughly:this.roughly,type: this.value,
                mdContent: this.mdContent, content: this.content,userId:this.$store.state.User.account,
                username:this.$store.state.User.userName}).then(res=>{
                if (res.data.flag === "md_save_succeed") {
                    this.$message({
                        message: '上传成功',
                        showClose: true,
                        type: 'success',
                        center: true,
                    });
                }
            });
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
    height: 100%;
    /*overflow: auto;*/
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
    /*-webkit-box-shadow: 0 1px 1px rgb(0 0 0 / 5%);*/
    /*box-shadow: 0 1px 1px rgb(0 0 0 / 5%);*/
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
    /*display: flex;*/
    /*align-items: center;*/
    /*justify-content: center;*/
}
.sp-operation-bar {
    width: 100%;
    height: 60px;
    padding: 10px;
    /*background: #27ae60;*/
}
.sp-inout-title {
    width: 10%;
    height: 40px;
    margin-right: 10px;
}
.sp-inout-roughly {
    width: 20%;
    height: 40px;
    margin-right: 10px;
}
.sp-space{
    margin-right: 10px;
}

.sp-mavon {
    /*background: #27ae60;*/
}
.v-note-wrapper{
    z-index: 1;
}
</style>