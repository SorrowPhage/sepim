<template>
    <div>
        <div v-if="files.length">
            <SimpleMdList v-for="md in files.slice((currentPage-1)*pagesize,currentPage*pagesize)"
                          :key="md.id" :describe="md.roughly"
                          :file_id="md.id"
                          :title="md.title"
                          :type="md.type"
                          :url="md.url"></SimpleMdList>
            <div class="page-box">
                <el-pagination
                    :page-size="pagesize"
                    :page-sizes="[5, 10, 15, 20]"
                    :total="this.files.length"
                    background
                    layout="total, sizes, prev, pager, next, jumper"
                    next-text="下一页"
                    prev-text="上一页"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange">
                </el-pagination>
            </div>
        </div>
        <div v-else>
            <el-empty></el-empty>
        </div>
    </div>
</template>

<script>
import SimpleMdList from "@/components/userdetail/details/list/SimpleMdList";

export default {
    name: "FolderList",
    components: {SimpleMdList},
    props: ['files'],
    data() {
        return {
            currentPage: 1,
            pagesize: 10,
        }
    },
    methods: {
        handleSizeChange(val) {
            this.pagesize = val;
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
    },
}
</script>

<style scoped>
.page-box {
    padding: 15px;
    text-align: center;
}
</style>