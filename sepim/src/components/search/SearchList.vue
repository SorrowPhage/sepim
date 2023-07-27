<template>
    <div class="main">
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <div class="pane-body">
                            <div class="sp-search-box">
                                <div class="sp-search-opt">
                                    <el-menu
                                        default-active="/index.html/sl/rc"
                                        class="el-menu-vertical-demo"
                                        :router="true">
                                        <el-menu-item index="/index.html/sl/rc" :route="{name:'rc',query:{q:$store.state.Search.searchContent}}">
                                            <span slot="title">Repository</span>
                                        </el-menu-item>
                                        <el-menu-item index="/index.html/sl/uc" :route="{name:'uc',query:{q:$store.state.Search.searchContent}}">
                                            <span slot="title">User</span>
                                        </el-menu-item>
                                    </el-menu>
                                </div>
                                <div class="sp-search-list">
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
export default {
    name: "SearchList",
    methods: {
        handleSelect(key, keyPath) {
        },
    }
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
    padding: 5px;
}

.sp-search-box {
    margin: auto;
    width: 50%;
    /*background-color: #227cf9;*/
    display: flex;
}
.sp-search-opt{
    width: 20%;
}
.sp-search-list{
    width: 80%;
    padding: 0 10px;
}
</style>