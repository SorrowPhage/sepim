<template>
    <div>
        <el-table
            :data="$store.state.Folder.list.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            style="width: 100%"
        >
            <el-table-column
                label="#"
                type="index"
                width="50">
            </el-table-column>
            <el-table-column
                prop="id"
                label="id"
                width="180">
            </el-table-column>
            <el-table-column
                prop="title"
                label="文件名"
                width="180">
            </el-table-column>
            <el-table-column
                prop="type"
                label="类型">
            </el-table-column>
            <el-table-column
                prop="state"
                label="状态">
            </el-table-column>
        </el-table>
        <div class="page-box">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :page-sizes="[5, 10, 15, 20]"
                :page-size="pagesize"
                layout="total, sizes, prev, pager, next, jumper"
                background
                prev-text="上一页"
                next-text="下一页"
                :total="this.$store.state.Holiday.leaves.length">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
    name: "DocList",
    data(){
        return{
            currentPage:1,
            pagesize:10,
        }
    },
    methods:{
        handleSizeChange(val) {
            this.pagesize=val;
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
    }
}
</script>

<style scoped>
.el-table .warning-row {
    background: oldlace;
}

.el-table .success-row {
    background: #f0f9eb;
}
.page-box{
    padding: 15px;
    text-align: right;
}
</style>