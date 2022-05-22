<template>
    <div class="main">
        
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <header class="table-heading">详情</header>
                        <div class="pane-body">
                            <div class="block">
                                <el-carousel height="250px">
                                    <el-carousel-item v-for="item in 4" :key="item">
                                        <h3 class="small">{{ item }}</h3>
                                    </el-carousel-item>
                                </el-carousel>
                            </div>
                            <hr>
                            <div>
                                <el-calendar v-model="value">
                                </el-calendar>
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
    name: 'MainContext',
    data() {
        return {
            value: new Date()
        }
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
    background-color: rgb(228, 228, 228);
    height: 100%;
    overflow: auto;
}
.page-heading {
    position: fixed;
    width: 100%;
    padding: 15px;
    position: relative;
}

.page-heading h3 {
    display: block;
    font-size: 1.17em;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
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
    -webkit-box-shadow: 0 1px 1px rgb(0 0 0 / 5%);
    box-shadow: 0 1px 1px rgb(0 0 0 / 5%);
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
</style>