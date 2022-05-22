<template>
    <div class="main">
        <div class="page-heading">
            <h3>个人信息</h3>
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>个人中心</el-breadcrumb-item>
                <el-breadcrumb-item>个人信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <header class="table-heading">详情</header>
                        <div class="pane-body">
                            <!--                            <section>-->
                            <div class="avatar_box">
                                <el-avatar :size="100" :src="avatar_url" class="avatar"></el-avatar>
                                
                                <el-button class="replace" type="primary" @click="dialogVisible = true">修改头像</el-button>
                                
                                <el-dialog
                                    :before-close="handleClose"
                                    :visible.sync="dialogVisible"
                                    center
                                    title="图片剪切"
                                    width="70%"
                                >
                                    
                                    <div class="cropper-box">
                                        <div class="cropper-warp">
                                            <div class="cropper">
                                                <vueCropper
                                                    ref="cropper"
                                                    :autoCrop="option.autoCrop"
                                                    :autoCropHeight="option.autoCropHeight"
                                                    :autoCropWidth="option.autoCropWidth"
                                                    :canMove="option.canMove"
                                                    :canMoveBox="option.canMoveBox"
                                                    :canScale="option.canScale"
                                                    :centerBox="option.centerBox"
                                                    :fixed="option.fixed"
                                                    :fixedBox="option.fixedBox"
                                                    :fixedNumber="option.fixedNumber"
                                                    :full="option.full"
                                                    :high="option.high"
                                                    :img="option.img"
                                                    :info="option.info"
                                                    :mode="option.mode"
                                                    :original="option.original"
                                                    :outputSize="option.size"
                                                    :outputType="option.outputType"
                                                    @imgLoad="imgLoad"
                                                    @realTime="realTime"
                                                ></vueCropper>
                                            </div>
                                        </div>
                                        <!--头像预览-->
                                        <div class="preview">
                                            <div :style="previews.div" style="overflow: hidden">
                                                <img :src="previews.url" :style="previews.img" alt="加载失败">
                                            </div>
                                        </div>
                                    
                                    </div>
                                    
                                    <span slot="footer" class="dialog-footer">
                                            <div class="select-avatar-box">
                                                 <input ref="upload_input_cropper"
                                                        accept="image/*"
                                                        class="hiddenInput"
                                                        type="file"
                                                        @change="selectAvatar"/>
                                                <el-button type="primary" @click="transferUploadInput">选择头像</el-button>
                                                
                                            <el-button icon="el-icon-zoom-in" plain size="mini" type="success"
                                                       @click="changeScale(1)">放大</el-button>
                                            <el-button icon="el-icon-zoom-out" plain size="mini" type="success"
                                                       @click="changeScale(-1)">缩小</el-button>
                                            <el-button plain size="mini" type="success"
                                                       @click="rotateLeft">↺ 左旋转</el-button>
                                            <el-button plain size="mini" type="success"
                                                       @click="rotateRight">↻ 右旋转</el-button>
                                             </div>
                                            <div class="return-btn-box">
                                                <el-button @click="dialogVisible = false">取 消</el-button>
                                                <el-button type="primary" @click="uploadImg('base64')">确 定</el-button>
                                            </div>
                                    </span>
                                </el-dialog>
                            
                            </div>
                            <div class="userinfo_box">
                                <el-form ref="form" :model="form" label-width="100px">
                                    <el-form-item label="账号：">
                                        <span class="sp-span">{{ userInfo.id }}</span>
                                    </el-form-item>
                                    <el-form-item label="用户名：">
                                        <el-input v-model="userInfo.username"></el-input>
                                    </el-form-item>
                                    <el-form-item label="邮箱：">
                                        <el-input v-model="userInfo.email"></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button type="primary" @click="updateUserInfo">保存</el-button>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <!--                            </section>-->
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapGetters, mapState} from 'vuex'
import axios from "axios"
import {sessionReplaceStore} from "@/utils/session_util"
import {VueCropper} from 'vue-cropper'

export default {
    name: "CheckUserInfo",
    components: {VueCropper},
    data() {
        return {
            dialogVisible: false,
            form: {
                birthday: '',
                circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
            },
            option: {
                img: "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
                outputSize: 1, //剪切后的图片质量（0.1-1）
                full: false, //输出原图比例截图 props名full
                outputType: "png", //裁剪生成图片的格式
                canMove: false, //上传图片是否可以移动
                original: false, //上传图片按照原始比例渲染
                canMoveBox: true, //截图框是否可以拖动
                autoCrop: true, //是否默认生成截图框
                autoCropWidth: 100, //默认截图框宽度
                autoCropHeight: 100, //默认生成截图框高度
                fixedBox: false, //固定截图框大小
                fixedNumber: [1, 1], //截图框的宽高比例
                mode: "auto 200px", //图片默认渲染方式
                info: false, //裁剪框的大小信息
                canScale: true, //图片是否允许滚轮缩放
                fixed: false, //是否开启截图框宽高固定比例
                centerBox: true, //截图框是否被限制在图片里面
                high: true, //是否按照设备的dpr 输出等比例图片
            },
            previews: {},
            userInfo: {
                id: this.$store.state.User.account,
                username: this.$store.state.User.userName,
                email: this.$store.state.User.email,
            }
        }
    },
    computed: {
        ...mapState("User", ["userName", "account", "email"]),
        ...mapGetters("User", ["avatar_url"]),
    },
    methods: {
        transferUploadInput() {
            this.$refs.upload_input_cropper.click()
        },
        //选择图片
        selectAvatar(e) {
            let $target = e.target
            let file = $target.files[0]
            let reader = new FileReader();
            reader.onload = (data) => {
                let src = data.target.result;
                // this.$store.commit("User/changeAvatar", src);
                this.option.img = src;
            };
            reader.readAsDataURL(file);
        },
        //图片上传
        uploadImg(type) {
            if (type === 'base64') {
                this.$refs.cropper.getCropData((data) => {
                    this.$store.commit("User/changeAvatar", data);
                    axios.post("http://localhost:8080/api/avatar", {file: this.base64toFile(data), id: this.account},
                        {headers: {'Content-Type': 'multipart/form-data'}}).then(response => {
                            if (response.data.flag === "avatar_set_succeed") {
                                this.$store.commit("User/changeAvatar", response.data.data);
                            }
                        },
                        error => {
                            console.log(error.message)
                        })
                })
                this.dialogVisible = false
            }
        },
        //base64转Fiel文件
        base64toFile(dataBase64, filename = 'file') {
            let arr = dataBase64.split(',')
            let mime = arr[0].match(/:(.*?);/)[1]  //设置file文件流的type名称
            let suffix = mime.split('/')[1]  //设置file文件流的name名称
            const bstr = window.atob(arr[1]);
            let n = bstr.length
            const u8arr = new Uint8Array(n)
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n)
            }
            return new File([u8arr], `${filename}.${suffix}`, {
                type: mime
            })
        },
        
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => {
                });
        },
        //预览
        realTime(data) {
            // console.log("realTime");
            this.previews = data;
        },
        imgLoad(msg) {
            // console.log("imgLoad");
            // console.log("@imgLoad",msg);
        },
        //图片缩放
        changeScale(num) {
            num = num || 1
            this.$refs.cropper.changeScale(num)
        },
        //向左旋转
        rotateLeft() {
            this.$refs.cropper.rotateLeft()
        },
        //向右旋转
        rotateRight() {
            this.$refs.cropper.rotateRight()
        },
        updateUserInfo() {
            axios.post("http://localhost:8080/api/update", this.userInfo).then(res => {
                if (res.data.flag === "user_info_update_succeed") {
                    this.$store.commit("User/getUserInfo", res.data.data);
                    console.log(res.data.data)
                }
            })
        }
    },
    created() {
        sessionReplaceStore('store');
        // 发生页面销毁事件前将store数据存储至sessionStoreage中
        window.addEventListener('beforeunload', () => {
            sessionStorage.setItem('store', JSON.stringify(this.$store.state))
        });
    },
};
</script>

<style scoped>
/*会影响登录页面的样式*/
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
    position: fixed;
    width: 100%;
    padding: 15px;
    position: relative;
}

.page-heading h3 {
    display: block;
    font-size: 1.17em;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
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
    display: flex;
    align-items: center;
    justify-content: center;
}


table {
    border-collapse: collapse;
    border-spacing: 0;
}

.table thead > tr > th,
.table tbody > tr > td {
    padding: 10px;
}

.table-bordered > thead > tr > th {
    border-bottom-width: 2px;
}

.table-bordered > thead > tr > th,
.table-bordered > tbody > tr > td {
    border: 1px solid #ddd;
}

.table-striped > tbody > tr:nth-child(odd) > td,
.table-striped > tbody > tr:nth-child(odd) > th {
    background-color: #f9f9f9e1;
}

th {
    text-align: left;
}

.sp-span {
    padding: 0 15px;
}

.avatar_box {
    width: 300px;
    /*border-right: 1px solid #3083ff;*/
}

.avatar {
    margin-left: 30%;
}

.replace {
    width: 100px;
    margin-left: 30%;
    text-align: center;
    margin-top: 5px;
}

.replace:hover {
    cursor: pointer;
}

.hiddenInput {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: none;
}

.cropper {
    height: 250px;
    width: 400px;
}

.preview {
    height: 300px;
    width: 400px;
    display: flex;
    justify-content: center; /*水平居中*/
    align-items: center; /*垂直居中*/
}

.cropper-box {
    display: flex;
    flex-direction: row;
    justify-content: center;
}

.select-avatar-box {
    /*height: 50px;*/
    text-align: left;
    float: left;
}
.return-btn-box{
    text-align: right;
}
.cropper-warp {
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>