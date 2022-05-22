<template>
    <div class="main">
        <div class="page-heading">
            <h3>个人信息</h3>
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>个人中心</el-breadcrumb-item>
                <el-breadcrumb-item>修改信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <header class="table-heading">详情</header>
                        <div class="pane-body">
                            <section>
                                <div class="user-setting-wrap">
                                    <form>
                                        <div class="el-form-item user-nick-rel-name">
                                            <label class="el-form-item__label">账号:</label>
                                            <div class="el-form-item__content">
                                                <span class="userinfo-descript">470565129</span>
                                            </div>
                                        </div>

                                        <div class="el-form-item user-nick-rel-name">
                                            <label class="el-form-item__label">用户名:</label>
                                            <div class="el-form-item__content">
                                                <div class="el-input">
                                                    <input
                                                            class=".el-input__inner"
                                                            type="text"
                                                            value="Admin"
                                                    />
                                                </div>
                                            </div>
                                        </div>

                                        <div class="el-form-item user-nick-rel-name">
                                            <label class="el-form-item__label">头像:</label>
                                            <div class="el-input">
                                                <div>
                                                    <img src="" alt=""/>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="el-form-item user-nick-rel-name">
                                            <label class="el-form-item__label">性别:</label>
                                            <div class="el-input">
                                                <span>男</span>
                                            </div>
                                        </div>

                                        <div>
                                            <label class="el-form-item__label">出生日期:</label>
                                            <div class="el-input">
                                                <div>
                                                    <div class="block">
                                                        <el-date-picker
                                                                v-model="birthday"
                                                                type="date"
                                                                placeholder="选择日期"
                                                        >
                                                        </el-date-picker>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <hr/>
                                <button>保存</button>
                            </section>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SetUserInfo",
        data() {
            return {
                birthday: "",
            };
        },
    };
</script>

<style>
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

    .user-setting-warp {
        padding: 20px 20px 0;
        position: relative;
    }

    .el-form-item {
        margin-bottom: 22px;
    }

    .user-nick-rel-name {
        float: left;
        width: 100%;
    }

    .el-form-item__label {
        width: 95px;
        text-align: right;
        margin-right: 20px;
        float: left;
        line-height: 30px;
        padding: 0;
    }

    .el-form-item__content {
        line-height: 30px;
    }

    .userinfo-descript {
        font-size: 14px;
        color: #898989;
    }

    .el-input {
        position: relative;
        font-size: 14px;
    }

    .el-input__inner {
        float: left;
        width: 225px;
        height: 30px;
        margin-right: 40px;
    }
</style>