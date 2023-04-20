<template>
    <div>
    <div class="music_list_column" v-show="isDevelop" @click="develop">
        <div class="music_list_column_avatar">
<!--            <el-avatar :size="60" :src="imgUrl" shape="square"></el-avatar>-->
            <span class="sp-avatar">
                <img :src="imgUrl">
            </span>
        </div>
        <div class="music_list_column_content">
            <div class="content_name">
                {{ name }}
                <div class="content_dropdown">
                    <el-dropdown @command="handleCommand">
                        <span class="el-dropdown-link">
                            <i class="el-icon-arrow-down"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="play">播放</el-dropdown-item>
                            <el-dropdown-item command="dialogVisible">设置</el-dropdown-item>
<!--                            <el-dropdown-item>删除</el-dropdown-item>-->
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
                <el-dialog
                    :visible.sync="dialogVisible"
                    append-to-body
                    title="歌单设置"
                    width="882px"
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
                            <div>
                                <el-form ref="form" :model="music" label-width="100px">
                                    <el-form-item label="歌单名：">
                                        <el-input v-model="music.musicListName"></el-input>
                                    </el-form-item>
                                    <el-form-item label="描述：">
                                        <el-input v-model="music.describe"></el-input>
                                    </el-form-item>
                                </el-form>
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
                        </div>
                        <div class="return-btn-box">
                            <el-button @click="dialogVisible = false">取 消</el-button>
                            <el-button type="primary" @click="updateMusicListInformation('base64')">确 定</el-button>
                        </div>
                    </span>
                </el-dialog>
            </div>
            <div class="content_de">{{ des }}</div>
        </div>
    </div>
<!--    <div>-->
<!--        <router-view></router-view>-->
<!--    </div>-->
    </div>
</template>

<script>
import {mapState} from "vuex";
import axios from "axios";
import pubsub from 'pubsub-js'

export default {
    name: "MusicListColumn",
    props: ['pictureUrl', 'musicListName', 'describe', 'id'],
    data() {
        return {
            isDevelop: true,
            dialogVisible: false,
            option: {
                img: "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
                // img: this.pictureUrl,
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
            music: {
                musicListName: this.musicListName,
                describe: this.describe,
            },
            imgUrl: this.pictureUrl,
            des: this.describe,
            name:this.musicListName
        }
    },
    computed: {
        ...mapState("User", ["account"]),
    },
    methods: {
        handleCommand(command) {
            if (command === "play") {
                console.log(1)
            } else if (command === "dialogVisible") {
                this.dialogVisible = true;
            }
        },
        //选择图片
        selectAvatar(e) {
            let $target = e.target
            let file = $target.files[0]
            let reader = new FileReader();
            reader.onload = (data) => {
                // this.$store.commit("User/changeAvatar", src);
                this.option.img = data.target.result;
            };
            reader.readAsDataURL(file);
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
        transferUploadInput() {
            this.$refs.upload_input_cropper.click()
        },
        updateMusicListInformation(type) {
            console.log(1)
            if (type === 'base64') {
                console.log()
                this.$refs.cropper.getCropData((data) => {
                    axios.post("http://localhost:8080/api/list/update", {
                            file: this.base64toFile(data),
                            id: this.id, name: this.music.musicListName, describe: this.music.describe
                        },
                        {headers: {'Content-Type': 'multipart/form-data'}}).then(response => {
                            if (response.data.flag === "music_list_upload_success") {
                                // this.$store.commit("User/changeAvatar", response.data.data);
                                console.log(response.data.data);
                                this.des = response.data.data.describe;
                                this.imgUrl = response.data.data.listImage;
                                this.name = response.data.data.name;
                                this.$message({
                                    message: '修改成功',
                                    showClose: true,
                                    type: 'success',
                                    center: true,
                                });
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
        develop() {
            // this.isDevelop = false;
            this.$router.push({
                name: 'develop',
                query:{
                    id: this.id,
                }
            })
        },
        },
    mounted() {
        // this.pubId = pubsub.subscribe('isDev',(msgName,data)=>{
        //     this.isDevelop = data;
        // })
    },
    // beforeDestroy() {
    //     pubsub.unsubscribe(this.pubId);
    // }
}
</script>

<style scoped>
.music_list_column {
    display: flex;
    cursor: pointer;
    margin-top: 5px;
    background-color: rgba(255, 255, 255, .5);
}

.music_list_column_avatar {
    width: 60px;
    height: 60px;
    
    /*display: inline;*/
    /*float: left;*/
}
.sp-avatar{
    height: 60px;
    width: 60px;
    line-height: 60px;
    display: inline-block;
    box-sizing: border-box;
    text-align: center;
    overflow: hidden;
    color: #fff;
    background: #C0C4CC;
    font-size: 14px;
}
.sp-avatar>img{
    display: block;
    height: 100%;
    vertical-align: middle;
}

.music_list_column_content {
    height: 60px;
    /*background: white;*/
    position: relative;
    /*left: 3px;*/
    flex-grow: 1;
    
    /*display: inline;*/
    /*float: left;*/
}

.content_name {
    height: 30px;
    /*background: #00a800;*/
    font-weight: bold;
    font-size: 15px;
    font-family: ui-monospace, SFMono-Regular, SF Mono, Menlo, Consolas, Liberation Mono, monospace !important;
}

.content_dropdown {
    float: right;
}

.content_de {
    height: 30px;
    color: rgba(0, 0, 0, 0.7);
    /*color: #999;*/
    font-size: 10px;
    /*background: black;*/
}

.el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
}

.el-icon-arrow-down {
    font-size: 12px;
}

.cropper-box {
    display: flex;
    flex-direction: row;
    justify-content: center;
}

.cropper-box {
    display: flex;
    flex-direction: row;
    justify-content: center;
}

.cropper {
    height: 250px;
    width: 400px;
}

.preview {
    padding: 3px;
    height: 300px;
    width: 400px;
    display: flex;
    justify-content: center; /*水平居中*/
    align-items: center; /*垂直居中*/
}

.select-avatar-box {
    /*height: 50px;*/
    text-align: left;
    float: left;
}

.return-btn-box {
    text-align: right;
}

.hiddenInput {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: none;
}

.sp-span {
    padding: 0 15px;
}
</style>