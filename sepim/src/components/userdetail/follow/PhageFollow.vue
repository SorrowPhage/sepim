<template>
    <div style="margin-top: 50px;width: 50%;margin-left: 25%">
        <div class="title">PHAGE</div>
        <div v-if="list.length">
            <PEFollow v-for="user in list"
                      :key="user.user.id" :account="user.user.id" :avatar-url="user.user.avatarUrl" :username="user.user.username"
            ></PEFollow>
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
import PEFollow from "@/components/userdetail/follow/PEFollow";
import axios from "axios";
export default {
    name: "PhageFollow",
    components: {PEFollow},
    data() {
        return{
            list: [],
            currentPage: 1,
            pageSize: 10,
            total: 0,
            pages: 0,
        }
    },
    watch:{
        '$route.query.account':{
            handler() {
                this.loadData();
            },
        }
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
            console.log(this.$route.query.account)
            axios.post("http://localhost:8080/api/uf/list-fans", {
                followedId: this.$route.query.userId,
                pageIndex: this.currentPage, pageSize: this.pageSize
            }).then(res => {
                if (res.data.flag === "200") {
                    // this.list = res.data.data.records;
                    // this.pages = res.data.data.pages;
                    // this.total = res.data.data.total;
                    // this.pagesize = res.data.data.pagesize;
                    this.list = res.data.data;
                }
            })
        },
    },
    mounted() {
        this.loadData();
    }
    
}
</script>

<style scoped>
.title {
    width: 100%;
    border-bottom: solid 1px gray;
    padding-bottom: 10px;
    font-size: 18px;
    color: #222;
}
</style>