<template>
    <div class="sp-search-content-box">
        <div v-if="list.length">
            <div  class="d-flex flex-column flex-md-row flex-justify-between border-bottom pb-3 position-relative">
                <h3><span class="v-align-middle">Showing {{list.length}}  available repository results </span></h3>
            </div>
            <SimpleMdList v-for="md in list.slice((currentPage-1)*pagesize,currentPage*pagesize)"
                    :key="md.id" :describe="md.roughly" :file_id="md.id" :title="md.title" :user="md.user.username"
                    :type="md.type" :url="md.url" :isShow="$store.state.Detail.id===$store.state.User.account"></SimpleMdList>
            <div class="page-box">
                <el-pagination
                    :page-size="pagesize"
                    :page-sizes="[5, 10, 15, 20]"
                    :total="this.list.length"
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
import axios from "axios";
// import MdList from "@/components/depository/list/MdList";
import SimpleMdList from "@/components/userdetail/details/list/SimpleMdList";
export default {
    name: "RepositoryContent",
    components: {SimpleMdList},
    data() {
        return {
            list: [],
            currentPage: 1,
            pagesize: 10,
        };
    },
    methods:{
        handleSizeChange(val) {
            this.pagesize = val;
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
    },
    created() {
        axios.get("http://localhost:8080/api/search/rep", {params: {q: this.$route.query.q}}).then(res => {
            if (res.data.flag === "search_rep_succeed") {
                this.list = res.data.data;
            }
        });
    }
}
</script>

<style scoped>
.sp-search-content-box{
    padding: 5px;
}
.page-box{
    padding: 15px;
    text-align: center;
}
.d-flex {
    display: flex !important;
}
.pb-3 {
    padding-bottom: 5px !important;
}
.position-relative {
    position: relative !important;
}
.flex-md-row {
    flex-direction: row !important;
}
.flex-justify-between {
    justify-content: space-between !important;
}
.border-bottom {
     border-bottom: 1px solid #d0d7de !important;
 }
.v-align-middle {
    vertical-align: middle !important;
}
h3 {
    font-size: 20px;
    font-weight: 600;
}
</style>