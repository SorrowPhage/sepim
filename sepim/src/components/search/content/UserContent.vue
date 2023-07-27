<template>
    <div class="sp-search-content-box">
        <div v-if="list.length">
            <div  class="d-flex flex-column flex-md-row flex-justify-between border-bottom pb-3 position-relative">
                <h3><span class="v-align-middle">{{list.length}} users </span></h3>
            </div>
<!--            <UserList v-for="user in list.slice((currentPage-1)*pagesize,currentPage*pagesize)"-->
<!--                      :key="user.id" :account="user.id" :avatar-url="user.avatarUrl" :username="user.username"-->
<!--            ></UserList>-->
            <UserList v-for="user in list"
                      :key="user.id" :account="user.id" :avatar-url="user.avatarUrl" :username="user.username"
            ></UserList>
            <div class="page-box">
                <el-pagination
                    :page-size="pageSize"
                    :page-sizes="[5, 10, 15, 20]"
                    :total="total"
                    :page-count="pages"
                    background
                    layout="total, prev, pager, next, jumper"
                    next-text="下一页"
                    prev-text="上一页"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange">
                </el-pagination>
            </div>
        </div>
        <div v-else>
            <el-empty image="http://localhost:8088/upload/sepim/state/empty.jpg"></el-empty>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import UserList from "@/components/search/content/user/UserList";

export default {
    name: "UserContent",
    components: {UserList},
    data() {
        return {
            list: [],
            currentPage: 1,
            pageSize: 10,
            total: 0,
            pages: 0,
        };
    },
    watch: {
        '$route.query.q': {
            handler() {
                this.loadData();
            },
        },
    },
    methods: {
        handleSizeChange(val) {
            this.pagesize = val;
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.loadData();
        },
        loadData() {
            axios.post("http://localhost:8080/api/search/user", {title: this.$route.query.q,
                pageIndex: this.currentPage, pageSize: this.pageSize}).then(res => {
                if (res.data.flag === "200") {
                    this.list = res.data.data.records;
                    this.pages = res.data.data.pages;
                    this.total = res.data.data.total;
                    this.pagesize = res.data.data.pagesize;
                }
            })
        },
    },
    created() {
        this.loadData();
    }
}
</script>

<style scoped>

.page-box {
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