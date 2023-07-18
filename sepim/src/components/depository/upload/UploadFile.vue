<template>
    <div class="sp-upload">
        <el-button type="primary"  @click.native  ="drawer = true" plain>上传</el-button>
        <el-drawer
            title="文件上传"
            :size="500"
            :visible.sync="drawer"
            :direction="direction"
            :before-close="handleClose">
            
            <div style="display: flex;justify-content: center">
                <el-upload
                    ref="upload"
                    class="upload-demo"
                    v-show="ud"
                    :auto-upload="false"
                    :before-remove="beforeRemove"
                    :data="{userId:$store.state.User.account,roughly:des,title:title,type: this.value}"
                    :file-list="fileList"
                    :limit="1"
                    :on-exceed="handleExceed"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :on-success="storeSuccess"
                    :on-error="storeError"
                    action="http://localhost:8085/md/store"
                    drag
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </div>
    
            <div style="padding: 5px;margin-top: 16px">
                <label class="sp-text">
                    <div style="width: 90%;flex-grow: 1;">
                            <textarea class="sp-textarea" style="height: 34px;" rows="1" placeholder="文章标题" maxlength="20" v-model="title">
                            </textarea>
                    </div>
                    <div class="sp-text-title-box">
                        <div class="sp-text-box-left"></div>
                        <div class="sp-text-box-title-center">
                            <span class="sp-text-box-title">Title</span>
                        </div>
                        <div class="sp-text-box-right"></div>
                    </div>
                </label>
            </div>
            
            <div style="padding: 5px;margin-top: 16px">
                <label class="sp-text">
                    <div style="width: 90%;flex-grow: 1;">
                            <textarea class="sp-textarea" style="height: 80px;" rows="3" placeholder="描述你的文章" maxlength="40" v-model="des">
                            </textarea>
                    </div>
                    <div class="sp-text-box">
                        <div class="sp-text-box-left"></div>
                        <div class="sp-text-box-center">
                            <span class="sp-text-box-title">Description</span>
                        </div>
                        <div class="sp-text-box-right"></div>
                    </div>
                </label>
            </div>
            
            <div style="margin-top: 16px;padding: 0 25px">
                <el-select class="sp-space" v-model="value" placeholder="类型" >
                    <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    >
                    </el-option>
                </el-select>
            </div>
            
            <el-button size="small" style="position: absolute;bottom: 20px;right: 20px" type="primary" plain @click="submitUpload">SUBMIT
            </el-button>
        </el-drawer>
    </div>
</template>

<script>
export default {
    name: "UploadFile",
    data(){
        return{
            drawer: false,
            direction: 'rtl',
            list: null,
            fileList: [],
            des: '',
            title: '',
            options: [{
                value: '私有的',
                label: '私有的'
            }, {
                value: '公开的',
                label: '公开的'
            }],
            value: '私有的',
            ud: true,
        }
    },
    methods:{
        handleClose(done) {
            done();
        },
        submitUpload() {
            if (this.title === ''||this.title===null) {
                this.$message({
                    message: '标题不能为空',
                    showClose: true,
                    type: 'error',
                    center: true,
                });
                return;
            }
            this.$refs.upload.submit();
        },
        storeSuccess() {
            this.$message({
                message: '上传成功',
                showClose: true,
                type: 'success',
                center: true,
            });
            this.des = '';
            this.title = '';
        },
        storeError() {
            this.$message({
                message: '上传失败',
                showClose: true,
                type: 'error',
                center: true,
            });
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        handleExceed(files, fileList) {
            this.$message.warning(`只能上传一个文件`);
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },
    }
}
</script>

<style scoped>
.upload-demo{
    width: 90%;
}
.upload-demo>>>.el-upload {
    width: 100%;
}
.upload-demo>>>.el-upload-dragger{
    width: 100%;
}
.sp-upload{
    display: inline;
}
.sp-text{
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 0 16px;
}
.sp-textarea {
    width: 100%;
    /*padding: 0 16px;*/
    outline: none;
    border: none;
    display: flex;
    margin-top: 16px;
    resize: none;
}



.sp-text-box {
    height: 100px;
    width: 90%;
    position: absolute;
    display: flex;
    cursor: text;
}

.sp-text-title-box{
    height: 50px;
    width: 90%;
    position: absolute;
    display: flex;
    cursor: text;
}

.sp-text-box-left {
    border-left: 1px solid rgb(218, 220, 224);
    border-top: 1px solid rgb(218, 220, 224);
    border-bottom: 1px solid rgb(218, 220, 224);
    height: 100%;
    width: 12px;
    border-top-left-radius: 8px;
    border-bottom-left-radius: 8px;
}
/*.sp-text-box-left:hover{*/
/*    border-left: 1px solid rgb(32, 33, 36);*/
/*    border-top: 1px solid rgb(32, 33, 36);*/
/*    border-bottom: 1px solid rgb(32, 33, 36);*/
/*}*/
.sp-text-box-center {
    width: 84.5px;
    height: 100%;
    border-top: none;
    border-bottom: 1px solid rgb(218, 220, 224);
    transform: translateY(1px);
}

.sp-text-box-title-center{
    width: 39.5px;
    height: 100%;
    border-top: none;
    border-bottom: 1px solid rgb(218, 220, 224);
    transform: translateY(1px);
}

.sp-text-box-title{
    font-family: Inter, monospace;
    font-style: normal;
    font-weight: 600;
    text-transform: uppercase;
    line-height: 16px;
    letter-spacing: 1px;
    font-size: 13.5px !important;
    color: rgb(95, 99, 104);
    transform: translateY(-8px) scale(.75);
    display: inline-block;
    position: relative;
    cursor: auto;
    overflow: hidden;
    white-space: nowrap;
    will-change: transform;
    text-align: left;
}
.sp-text-box-right {
    flex-grow:1;
    height: 100%;
    border-bottom: 1px solid rgb(218, 220, 224);
    border-right: 1px solid rgb(218, 220, 224);
    border-top: 1px solid rgb(218, 220, 224);
    border-top-right-radius: 8px;
    border-bottom-right-radius: 8px;
}
</style>