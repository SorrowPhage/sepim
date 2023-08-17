<template>
    <div>
        <el-upload
            ref="upload"
            :auto-upload="false"
            :before-remove="beforeRemove"
            :data="{userId:userId}"
            :file-list="fileList"
            :limit="1"
            :on-exceed="handleExceed"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            action="http://localhost:8086/copyAuth/upload/roles"
            class="upload-demo">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button size="small" style="margin-left: 10px;" type="success" @click="submitUpload">上传到服务器
            </el-button>
        </el-upload>
        
        <el-button @click="testRole">角色下载</el-button>
<!--        <el-upload-->
<!--            ref="upload"-->
<!--            :auto-upload="false"-->
<!--            :before-remove="beforeRemove"-->
<!--            :data="{userId:userId}"-->
<!--            :file-list="fileList"-->
<!--            :limit="1"-->
<!--            :on-exceed="handleExceed"-->
<!--            :on-preview="handlePreview"-->
<!--            :on-remove="handleRemove"-->
<!--            action="http://localhost:8086/copyAuth/upload/user-permissions"-->
<!--            class="upload-demo">-->
<!--            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>-->
<!--            <el-button size="small" style="margin-left: 10px;" type="success" @click="submitUpload">上传到服务器-->
<!--            </el-button>-->
<!--        </el-upload>-->
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "UserOverviewData",
    data() {
        return{
            userId: 1,
            fileList: [],
        }
    },
    methods:{
        testRole() {
            axios.post("http://localhost:8086/copyAuth/export/role-excel", {userId: 2}, {responseType: "blob"}).then(res => {
                // 判断一下后台是否是成功的
                if (res.status === 200) {
                    // 获取流信息
                    const blob = new Blob([res.data]);
                    // 设置文件的名称
                    const fileName = '角色信息表.xls';
                
                    if ('download' in document.createElement('a')) { // 非IE下载
                    
                        // document.createElement()是在对象中创建一个对象
                        const elink = document.createElement('a');
                    
                        // js下载通过 download 属性下载 fileName 这个文件
                        elink.download = fileName;
                        // 样式 为 none
                        elink.style.display = 'none';
                    
                        //可以获取当前文件的一个内存URL
                        elink.href = URL.createObjectURL(blob);
                    
                        //body中添加一个子结点
                        document.body.appendChild(elink);
                    
                        elink.click();
                    
                        // 释放URL 对象
                        URL.revokeObjectURL(elink.href);
                    
                        // 删除指定的元素
                        document.body.removeChild(elink);
                    
                    }
                    console.log("导出成功")
                } else {
                    console.log("导出异常,未成功导出文件!")
                }
            })
        },
        
        
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
    }
}
</script>

<style scoped>

</style>