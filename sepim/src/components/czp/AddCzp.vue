<template>
    <div style="margin-top: 50px;">
        <el-upload style="display: inline-block"  name="file" ref="fileUploader" accept=".xls,.xlsx"
                   :action="fileUpload.action"
                   :data="uploadParam"
                   :file-list="fileUpload.fileList"
                   :on-success="fileUploadSuccess"
                   :on-error="fileUploadError"
        >
            <el-button type="primary">
                更新数据<span style="color: red; font-size: 16px;"></span>
            </el-button>
        </el-upload>
        <el-select v-model="batchNo" placeholder="请选择">
            <el-option
                v-for="item in batchNos"
                :key="item"
                :label="item"
                :value="item">
            </el-option>
        </el-select>
        <el-button @click="addCzpTree()">查询</el-button>
        <el-row>
            <el-col :span="24" class="el-main-content">
                <el-table :data="tableData" style="width: 100%;" :height="tableHeight">
                    <el-table-column prop="userId" label="用户编码"></el-table-column>
                    <el-table-column prop="name" label="用户姓名"></el-table-column>
                    <el-table-column prop="parentName" label="父级"></el-table-column>
                    <el-table-column prop="operaterName" label="操作人"></el-table-column>
                    <el-table-column prop="groupName" label="族群"></el-table-column>
                    <el-table-column prop="sex" label="性别"></el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <!-- 分页 -->
        <el-row>
            <el-col :span="24" style="text-align: center;">
                <el-pagination layout="prev, pager, next"
                               :page-count="pagination.pages"
                               :total="pagination.total"
                               :current-page.sync="searchParam.pageNum"
                               :page-size="pagination.pageSize"
                               @current-change="getDataList">
                </el-pagination>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import {windowHeight,uploadCzpTree} from "@/components/api/api";
import axios from "axios";
export default {
    name: "AddCzp",
    data() {
        return{
            tableData: [],
            uiController: {
                showDialog: false
            },
            tableHeight: (windowHeight() - 320),
            searchParam: {
                pageFlag: 1,
                pageNum: 1,
                pageSize: 12
            },
            uploadParam: {
                userId: '1',
            },
            fileUpload: {
                action: uploadCzpTree() ,
                fileList: []
            },
            pagination: {pages: 0, total: 0, pageSize: 12},
            batchNos:[],
            batchNo: '',
        }
    },
    mounted() {
        this.loadData();
    },
    methods:{
        addCzpTree() {
            axios.get("http://localhost:8080/api/czp/get",{params:{userId:1, batchNo: '20230915151825'}}).then(res=>{
                this.tableData = res.data.data
            })
        },
        getDataList() {
        
        },
        loadData() {
            axios.get("http://localhost:8080/api/czp/batchnos",{params:{userId:1}}).then(res=>{
                console.log(res);
                this.batchNos = res.data.data;
                this.batchNo = this.batchNos[0];
            })
        },
        fileUploadSuccess(result) {
            if (result != null) {
                console.log(result)
                console.log("上传成功")
            }
            this.fileUpload.fileList = []
        },
        fileUploadError(err) {
            this.$notify({
                title: "上传错误",
                message: err,
                type: "error",
                position: 'bottom-right'
            })
            this.fileUpload.fileList = []
        },
    }
}
</script>

<style scoped>

</style>