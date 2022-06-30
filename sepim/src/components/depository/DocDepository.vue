<template>
    <div class="main">
        <!--        <div class="page-heading">-->
        <!--            <h3>Depository</h3>-->
        <!--        </div>-->
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
<!--                        <header class="table-heading">DocumentList</header>-->
                        <div class="pane-body">
                            <div class="sp-md-list-box">
                                <div class="sp-md-list">
                                    <div class="sp-operation-bar">
                                        <input v-model="keyword" class="sp-search-private" placeholder="Search"
                                               type="text"/>
                                        <el-select v-model="value" class="sp-space" placeholder="类型"
                                                   style="width: 100px">
                                            <el-option
                                                v-for="item in options"
                                                :key="item.value"
                                                :label="item.label"
                                                :value="item.value"
                                            >
                                            </el-option>
                                        </el-select>
                                        <el-button type="primary" @click="newdoc">新建</el-button>
                                    </div>
                                    <div class="dropdown-divider"></div>
                                    <div v-if="folders.length">
                                        <MdList v-for="md in folders.slice((currentPage-1)*pagesize,currentPage*pagesize)"
                                                :key="md.id" :describe="md.roughly" :file_id="md.id" :title="md.title"
                                                :type="md.type" :url="md.url"></MdList>
                                        <div class="page-box" >
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
                                        <el-empty></el-empty>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
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
        ...mapState('Folder', ['list']),
        folders() {
            const arr = this.list.filter((folder) => {
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
    methods: {
        newdoc() {
            this.$router.push({
                name: 'newdoc'
            });
        },
        handleSizeChange(val) {
            this.pagesize = val;
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
    },
    created() {
        this.$store.dispatch("Folder/getMdList", this.$store.state.User.account);
    },
}
</script>

<style scoped>
* {
    box-sizing: border-box;
}

.main {
    margin-top: 50px;
    /*background-color: rgb(228, 228, 228);*/
    height: 100%;
    /*overflow: auto;*/
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
    padding: 10px;
}

.sp-search-private {
    width: 50%;
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
    width: 50%;
    /*width: 410px;*/
}

.sp-md-list-box {
    width: 100%;
    display: flex;
    justify-content: center;
    
}

</style>