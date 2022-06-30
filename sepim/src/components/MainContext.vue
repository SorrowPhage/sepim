<template>
    <div class="main">
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <div class="pane-body">
                            <div class="main-box">
                                <div class="sp-main-left">
                                    <el-menu
                                        default-active="2"
                                        class="el-menu-vertical-demo"
                                        :router="true"
                                        @open="handleOpen"
                                        @close="handleClose">
                                        <el-menu-item index="/index.html/main/el">
                                            <span slot="title">入门</span>
                                        </el-menu-item>
                                        <el-submenu index="1">
                                            <template slot="title">
                                                <span>编写文章</span>
                                            </template>
                                            <el-menu-item-group>
                                                <el-menu-item index="/index.html/main/bfag">基本格式和语法</el-menu-item>
                                            </el-menu-item-group>
                                        </el-submenu>
                                    </el-menu>
                                </div>
                                <div class="sp-main-center">
                                    <router-view></router-view>
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
import axios from "axios";
import {mapGetters,mapState} from "vuex";

export default {
    name: 'MainContext',
    data() {
        return {
            recommend: [],
            list: [],
        }
    },
    computed: {
        ...mapGetters("User", ['avatar_url'])
    },
    methods: {
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        newdoc() {
            this.$router.push({
                name: 'newdoc'
            });
        },
        readMd(id) {
            this.$router.push({
                name: 'read',
                query: {
                    id: id,
                }
            })
        },
    },
    mounted() {
        axios.get("http://localhost:8080/api/md/rank").then(res => {
            this.recommend = res.data.data;
            // this.loading = false;
        });
        axios.post("http://localhost:8080/api/md/list",{userId:this.$store.state.User.account}).then(res => {
            if (res.data.flag === "md_list_succeed") {
                this.list = res.data.data.reverse();
            }
        });
    }
};
</script>

<style scoped>
.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
    text-align: center;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}

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
    /*border: 1px solid transparent;*/
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
    /*padding: 15px;*/
}

.table {
    width: 100%;
    margin-bottom: 20px;
}

.table-bordered {
    border: 1px solid #ddd;
}

table {
    border-collapse: collapse;
    border-spacing: 0;
}

.table thead > tr > th,
.table tbody > tr > td {
    padding: 10px;
}

.table-bordered > thead > tr > th {
    border-bottom-width: 2px;
}

.table-bordered > thead > tr > th,
.table-bordered > tbody > tr > td {
    border: 1px solid #ddd;
}

.table-striped > tbody > tr:nth-child(odd) > td,
.table-striped > tbody > tr:nth-child(odd) > th {
    background-color: #f9f9f9e1;
}

th {
    text-align: left;
}
.main-box{
    width: 100%;
    /*display: flex;*/
}
.calendar-box{
    width: 30%;
}
.recommend-box{
    width: 70%;
    padding: 15px;
}
.md-box：{
    width: 100%;
}
.link-box{
    width: 100%;
}
.route-style {
    color:#80888c;
    text-decoration: none;
}

.route-style:hover {
    cursor: pointer;
    color: #0969da;
    text-decoration: underline;
}
.sp-main-left {
    width: 12.5%;
    min-width: 225px;
    font-weight: bold;
    border-right: solid 1px #e6e6e6;
    position: fixed;
    height: 100%;
    background-color: white;
    /*background-color: #227cf9;*/
}
.sp-main-left-title {
    /*text-align: center;*/
    width: 100%;
    /*display: flex;*/
    /*flex-wrap: wrap;*/
    /*justify-content: center;*/
    /*align-items: center;*/
}
.sp-main-left-content {
    width: 100%;
    /*display: flex;*/
    /*flex-wrap: wrap;*/
    /*justify-content: center;*/
    /*align-items: center;*/
}
.sp-main-center {
    /*width: 87.5%;*/
    /*background-color: #f6f8fa;*/
    padding: 10px;
    /*text-align: center;*/
    /*margin-left: 12.5%;*/
    display: block;
    overflow: hidden;
    margin-left: 225px;
}
h2{
    display: inline;
}
.sp-btn-place{
    display: inline;
    margin-left: 10px;
}
</style>