<template>
    <div>
        <el-avatar :size="100" :src="face_url" class="avatar"></el-avatar>
        <el-button class="replace" type="primary" @click="getCompetence()">人脸上传</el-button>
        <el-dialog
            :visible.sync="dialogVisible"
            title="上传"
            width="882px">
    
            <div class="camera_outer">
                <div class="pho-warp">
                    <div>
                        <video id="videoCamera" :width="videoWidth" :height="videoHeight" autoplay></video>
                        <canvas id="canvasCamera" style="display:none;" :width="videoWidth" :height="videoHeight"></canvas>
                    </div>
                    <div>
                        <div class="preview">
                            <img :src="pre"/>
                        </div>
                    </div>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <div class="return-btn-box">
                    <el-button @click="stopNavigator()">关闭摄像头</el-button>
                    <el-button type="primary" @click="setImage()">拍照</el-button>
                    <el-button type="primary" @click="uploadImg()">上传照片</el-button>
                </div>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import axios from "axios";
import {mapState} from "vuex";

export default {
    name: "PhotographComponent",
    computed: {
        ...mapState("User", ["face_url"]),
    },
    data () {
        return {
            dialogVisible: false,
            videoWidth: 300,
            videoHeight: 300,
            imgSrc: '',
            thisCancas: null,
            thisContext: null,
            thisVideo: null,
            pre: null,
        }
    },

    destroyed() {
        this.stopNavigator()
    },
    methods: {
        
        handleAvatarSuccess(res, file) {
            this.$emit('refreshDataList', res.data.url)
        },
        // 调用权限（打开摄像头功能）
        getCompetence() {
            var _this = this
            _this.dialogVisible = true;
            this.$nextTick(()=>{
                this.thisCancas = document.getElementById('canvasCamera')
                this.thisContext = this.thisCancas.getContext('2d')
                this.thisVideo = document.getElementById('videoCamera')
                // 旧版本浏览器可能根本不支持mediaDevices，我们首先设置一个空对象
                if (navigator.mediaDevices === undefined) {
                    navigator.mediaDevices = {}
                }
                // 一些浏览器实现了部分mediaDevices，我们不能只分配一个对象
                // 使用getUserMedia，因为它会覆盖现有的属性。
                // 这里，如果缺少getUserMedia属性，就添加它。
                if (navigator.mediaDevices.getUserMedia === undefined) {
                    navigator.mediaDevices.getUserMedia = function (constraints) {
                        // 首先获取现存的getUserMedia(如果存在)
                        var getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.getUserMedia
                        // 有些浏览器不支持，会返回错误信息
                        // 保持接口一致
                        if (!getUserMedia) {
                            return Promise.reject(new Error('getUserMedia is not implemented in this browser'))
                        }
                        // 否则，使用Promise将调用包装到旧的navigator.getUserMedia
                        return new Promise(function (resolve, reject) {
                            getUserMedia.call(navigator, constraints, resolve, reject)
                        })
                    }
                }
                
                var constraints = { audio: false, video: { width: this.videoWidth, height: this.videoHeight, transform: 'scaleX(-1)' } }
                navigator.mediaDevices.getUserMedia(constraints).then(function (stream) {
                    // 旧的浏览器可能没有srcObject
                    if ('srcObject' in _this.thisVideo) {
                        _this.thisVideo.srcObject = stream
                    } else {
                        // 避免在新的浏览器中使用它，因为它正在被弃用。
                        _this.thisVideo.src = window.URL.createObjectURL(stream)
                    }
                    _this.thisVideo.onloadedmetadata = function (e) {
                        _this.thisVideo.play()
                    }
                }).catch(err => {
                    console.log(err)
                })
            })
        },
        //  绘制图片（拍照功能）
        
        setImage() {
            var _this = this
            // 点击，canvas画图
            _this.thisContext.drawImage(_this.thisVideo, 0, 0, _this.videoWidth, _this.videoHeight)
            // 获取图片base64链接
            var image = this.thisCancas.toDataURL('image/png')
            this.realTime(image)
            
            _this.imgSrc = image
            const file = image
            const time = (new Date()).valueOf()
            const name = time + '.png'
            const conversions = this.base64ToFile(file, name)
            const data = new FormData()
            data.append('file', conversions)
            
        },
        // base64图片转file的方法（base64图片, 设置生成file的文件名）
        base64ToFile(base64, fileName) {
            // 将base64按照 , 进行分割 将前缀  与后续内容分隔开
            const data = base64.split(',')
            // 利用正则表达式 从前缀中获取图片的类型信息（image/png、image/jpeg、image/webp等）
            const type = data[0].match(/:(.*?);/)[1]
            // 从图片的类型信息中 获取具体的文件格式后缀（png、jpeg、webp）
            const suffix = type.split('/')[1]
            // 使用atob()对base64数据进行解码  结果是一个文件数据流 以字符串的格式输出
            const bstr = window.atob(data[1])
            // 获取解码结果字符串的长度
            let n = bstr.length
            // 根据解码结果字符串的长度创建一个等长的整形数字数组
            // 但在创建时 所有元素初始值都为 0
            const u8arr = new Uint8Array(n)
            // 将整形数组的每个元素填充为解码结果字符串对应位置字符的UTF-16 编码单元
            while (n--) {
                // charCodeAt()：获取给定索引处字符对应的 UTF-16 代码单元
                u8arr[n] = bstr.charCodeAt(n)
            }
            // 利用构造函数创建File文件对象
            // new File(bits, name, options)
            const file = new File([u8arr], `${fileName}.${suffix}`, {
                type: type
            })
            // 将File文件对象返回给方法的调用者
            return file
        },
    
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
        // 关闭摄像头
        stopNavigator() {
            this.dialogVisible = false;
            this.thisVideo.srcObject.getTracks()[0].stop()
        },
        realTime(data) {
            // console.log("realTime");
            this.pre = data;
        },
        uploadImg(){
            axios.post("http://localhost:8080/api/user/face",{file:this.base64toFile(this.pre)
                , id: this.$store.state.User.account},{headers: {'Content-Type': 'multipart/form-data'}}).then(res=>{
                if (res.data.flag === "200") {
                    this.$store.commit("User/changeFaceUrl", res.data.data);
                    console.log(res)
                    this.$message({
                        message: '上传成功',
                        showClose: true,
                        type: 'success',
                        center: true,
                    });
                } else if (res.data.flag === "400") {
                    this.$message({
                        message: '上传失败',
                        showClose: true,
                        type: 'error',
                        center: true,
                    });
                }else {
                    this.$message({
                        message: '一个人只能绑定一个账号',
                        showClose: true,
                        type: 'error',
                        center: true,
                    });
                }
            })
        }
    }
}
</script>

<style lang="less" scoped>
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
    width: 300px;
    margin-left: 50px;
    border: 1px solid;
}

.pho-warp {
    display: flex;
    justify-content: center;
    align-items: center;
}
.camera_outer {
    position: relative;
    overflow: hidden;
    // background: url("../../assets/img/user_0608_04.png") no-repeat center;
    background-size: 100%;
    
    video,
    canvas,
    .tx_img {
        -moz-transform: scaleX(-1);
        -webkit-transform: scaleX(-1);
        -o-transform: scaleX(-1);
        transform: scaleX(-1);
    }
    
    .btn_camera {
        position: absolute;
        bottom: 4px;
        left: 0;
        right: 0;
        height: 50px;
        background-color: rgba(0, 0, 0, 0.3);
        line-height: 50px;
        text-align: center;
        color: #ffffff;
    }
    
    .bg_r_img {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        top: 0;
    }
    
    .img_bg_camera {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        top: 0;
        
        img {
            width: 300px;
            height: 300px;
        }
        
        .img_btn_camera {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            height: 50px;
            line-height: 50px;
            text-align: center;
            background-color: rgba(0, 0, 0, 0.3);
            color: #ffffff;
            
            .loding_img {
                width: 50px;
                height: 50px;
            }
        }
    }
}

</style>