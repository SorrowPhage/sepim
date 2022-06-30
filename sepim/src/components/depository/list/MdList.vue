<template>
    <div>
    <div class="list-box">
        <div class="col-list">
            <div class="content-box">
                <span class="route-style" @click="readMd(file_id)">{{ title }}</span>
                <span></span>
                <span class="Label Label--secondary  v-align-middle ml-1 mb-1">{{ type }}</span>
                <div></div>
                <div class="f6 color-fg-muted mt-2">
                    {{describe}}
                </div>
            </div>
            <div class="operation-box">
                <el-popover
                    placement="bottom"
                    width="200"
                    trigger="click"
                    >
                    <div class="btn-box">
                        <el-button type="primary" icon="el-icon-edit" circle @click="edit(file_id)"></el-button>
                        <el-button type="danger" icon="el-icon-delete" circle @click="deleteMd(file_id)"></el-button>
                    </div>
                    <el-button slot="reference"  icon="el-icon-setting"></el-button>
                </el-popover>
            </div>
        </div>
    </div>
    <div class="dropdown-divider"></div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "MdList",
    props: ['file_id','url','describe', 'type', 'title'],
    methods:{
        readMd(id) {
                this.$router.push({
                    name: 'read',
                    query:{
                        id: id,
                    }
                })
        },
        edit(id) {
            this.$router.push({
                name:'edit',
                query:{
                    id: id,
                }
            })
        },
        deleteMd(id) {
            axios.get("http://localhost:8080/api/md/remove", {params: {id: id}}).then(res => {
                if (res.data.flag === "md_delete_succeed") {
                    this.$store.dispatch("Folder/getMdList", this.$store.state.User.account);
                    // this.$store.dispatch("Detail/getMdList", {id:this.$store.state.Detail.id,
                    //     account:this.$store.state.User.account});
                    this.$message({
                        message: '删除成功',
                        showClose: true,
                        type: 'success',
                        center: true,
                    });
                }
            });
        },
    }
}
</script>

<style scoped>

.list-box {
    padding: 12px 0;
}

.col-list {
    width: 100%;
    display: flex;
}

.route-style {
    color: #0969da;
    text-decoration: none;
}

.route-style:hover {
    cursor: pointer;
    color: #0969da;
    text-decoration: underline;
}

.Label {
    display: inline-block;
    font-size: 12px;
    font-weight: 500;
    line-height: 18px;
    white-space: nowrap;
    padding: 0px 7px;
    border-width: 1px;
    border-style: solid;
    border-image: initial;
    border-radius: 2em;
    border-color: #d0d7de;
}

.v-align-middle {
    vertical-align: middle !important;
}

.ml-1 {
    margin-left: 4px !important;
}

.mb-1 {
    margin-bottom: 4px !important;
}

.Label--secondary {
    color: #57606a;
    border-color: #d0d7de;
}

.dropdown-divider {
    display: block;
    height: 0;
    margin: 8px 0;
    border-top: 1px solid #d0d7de;
}
.f6 {
    font-size: 12px !important;
}
.mt-2 {
    margin-top: 8px !important;
}
.color-fg-muted {
    color: #57606a !important;
}
.content-box {
    width: 70%;
}
.operation-box{
    width: 30%;
    text-align: right;
}
.btn-box {
    display: flex;
    justify-content: center;
}
</style>