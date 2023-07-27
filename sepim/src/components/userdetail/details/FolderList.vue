<template>
        <div class="pane-body">
            <div class="sp-md-list-box">
                <div class="sp-md-list">
                    <div class="sp-operation-bar">
                        <input v-model="keyword" class="sp-search-private" placeholder="Search"
                               type="text"/>
<!--                        <el-select v-model="value" class="sp-space" placeholder="类型"-->
<!--                                   style="width: 100px">-->
<!--                            <el-option-->
<!--                                v-for="item in options"-->
<!--                                :key="item.value"-->
<!--                                :label="item.label"-->
<!--                                :value="item.value"-->
<!--                            >-->
<!--                            </el-option>-->
<!--                        </el-select>-->
<!--                        <el-button type="primary" @click="newdoc">新建</el-button>-->
                    </div>
                    <div class="dropdown-divider"></div>
                    <div v-if="folders.length">
                        <MdList v-for="md in folders.slice((currentPage-1)*pagesize,currentPage*pagesize)"
                                :key="md.id" :describe="md.roughly" :file_id="md.id" :title="md.title"
                                :type="md.type" :url="md.url" :isShow="$store.state.Detail.id===$store.state.User.account"></MdList>
                        <div class="page-box">
                            <el-pagination
                                :page-size="pagesize"
                                :page-sizes="[5, 10, 15, 20]"
                                :total="this.folders.length"
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
                        <el-empty image="http://localhost:8088/upload/sepim/state/empty.jpg"></el-empty>
                    </div>
                </div>
            </div>
        </div>
</template>

<script>
import MdList from "@/components/depository/list/MdList";
import {mapState} from "vuex";

export default {
    name: 'DocDepository',
    components: {MdList},
    data() {
        return {
            options: [{
                value: '所有',
                label: '所有'
            }, {
                value: '私有的',
                label: '私有的'
            }, {
                value: '公开的',
                label: '公开的'
            }],
            value: '所有',
            currentPage: 1,
            pagesize: 10,
            keyword: '',
        }
    },
    computed: {
        ...mapState('Detail', ['files']),
        folders() {
            const arr = this.files.filter((folder) => {
                return folder.title.indexOf(this.keyword) !== -1;
            });
            if (this.value !== "所有") {
                return arr.filter((folter) => {
                    return folter.type === this.value;
                });
            }
            return arr;
        },
    },
    watch:{
        '$route.query.account':{
            handler() {
                this.loadData();
            },
        }
    },
    methods: {
        newdoc() {
            this.$router.push({
                name: 'newdoc',
            });
        },
        handleSizeChange(val) {
            this.pagesize = val;
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        loadData() {
            this.$store.dispatch("Detail/getMdList", {id:this.$route.query.account,account:this.$store.state.User.account});
        },
    },
    mounted() {
        this.loadData()
    },
}
</script>

<style scoped>
* {
    box-sizing: border-box;
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

.pane-body {
    padding: 15px;
}


.dropdown-divider {
    display: block;
    height: 0;
    margin: 8px 0;
    border-top: 1px solid #d0d7de;
    width: 100%;
}

.sp-operation-bar {
    width: 100%;
    height: 60px;
    padding: 5px;
}

.sp-search-private {
    width: 100%;
    height: 40px;
    margin-right: 10px;
}

.sp-space {
    margin-right: 10px;
}

.page-box {
    padding: 15px;
    text-align: center;
}

.sp-md-list {
    width: 100%;
    /*width: 410px;*/
}

.sp-md-list-box {
    width: 100%;
    display: flex;
    justify-content: center;
    
}

</style>