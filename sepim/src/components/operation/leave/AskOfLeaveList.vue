<template>
    <div class="main">
        <div class="page-heading">
            <h3>请假</h3>
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>请假</el-breadcrumb-item>
<!--                <el-breadcrumb-item>个人信息</el-breadcrumb-item>-->
            </el-breadcrumb>
        </div>
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <header class="table-heading">详情</header>
                        <AskOfLeave></AskOfLeave>
                        <div class="pane-body">
                            <el-table
                                :data="$store.state.Holiday.leaves.slice((currentPage-1)*pagesize,currentPage*pagesize)"
                                border
                                style="width: 100%"
                                :row-class-name="tableRowClassName">
                                <el-table-column
                                    label="#"
                                    type="index"
                                    width="180">
                                </el-table-column>
                                <el-table-column
                                    prop="id"
                                    label="id"
                                    width="180">
                                </el-table-column>
                                <el-table-column
                                    prop="reason"
                                    label="理由"
                                    width="180">
                                </el-table-column>
                                <el-table-column
                                    prop="leaveTime"
                                    label="请假时间">
                                </el-table-column>
                                <el-table-column
                                    prop="releaseTime"
                                    label="结假时间">
                                </el-table-column>
                                <el-table-column
                                    prop="state"
                                    label="状态">
                                </el-table-column>
                            </el-table>
                        </div>
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
                    </section>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import AskOfLeave from "@/components/operation/leave/leave/AskOfLeave";
export default {
    name: 'AskOfLeaveList',
    components: {AskOfLeave},
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
        tableRowClassName({row}) {
            if (row.state === "审核不通过") {
                return 'warning-row';
            } else if (row.state === "审核通过") {
                return 'success-row';
            }
            return '';
        }
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
.page-box{
    padding: 15px;
    text-align: right;
}
.el-table .warning-row {
    background: oldlace;
}

.el-table .success-row {
    background: #f0f9eb;
}
</style>