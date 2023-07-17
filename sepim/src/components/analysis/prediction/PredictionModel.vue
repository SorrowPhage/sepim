<template>
    <div>
        <el-row>
            <el-col :span="24">
                <el-upload
                    ref="upload"
                    :auto-upload="false"
                    :before-remove="beforeRemove"
                    :data="{id:$store.state.User.account}"
                    :file-list="fileList"
                    :limit="1"
                    :on-exceed="handleExceed"
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    action="http://localhost:8085/file/save"
                    class="upload-demo">
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                    <el-button size="small" style="margin-left: 10px;" type="success" @click="submitUpload">上传到服务器
                    </el-button>
                </el-upload>
            </el-col>
        </el-row>
        
        <el-row>
            <el-col v-for="(file,index) in this.list" :key="index"  :span="24" @click.native="check(file.id)">{{file.url.substring(file.url.lastIndexOf("/")+1)}}</el-col>
        </el-row>

    
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "PredictionModel",
    data() {
        return {
            fileList: [],
            list: null,
        };
    },
    methods: {
        submitUpload() {
            this.$refs.upload.submit();
            // console.log(this.fileList);
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
        check(id) {
            this.$router.push({
                name: 'survey_csv',
                query:{
                    id: id,
                }
            })
        },
    },
    mounted() {
        axios.post("http://localhost:8080/api/file/get", {id: this.$store.state.User.account}).then(res => {
            if (res.data.flag === "200") {
                this.list = Object.assign([],res.data.data).map(item => {
                    return Object.assign({},item)
                })
            }
        });
        
    }
}
</script>

<style scoped>

</style>