<template>
    <div>
        <div class="operation-box">
            <el-button type="primary" @click="dialogVisible = true">请假</el-button>
        </div>
        <div>
            <el-dialog
                :visible.sync="dialogVisible"
                title="请假"
                width="60%"
            >
                <el-form ref="form" label-width="100px">
                    <el-form-item label="原因：">
                        <el-radio-group v-model="radio">
                            <el-radio :label="disease" border>病假</el-radio>
                            <el-radio :label="routine" border>事假</el-radio>
                            <el-radio :label="other" border>其他</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="请假时间：">
                        <div class="block">
                            <el-date-picker
                                v-model="leave_time"
                                :picker-options="pickerOptions"
                                align="right"
                                placeholder="选择日期时间"
                                type="datetime">
                            </el-date-picker>
                        </div>
                    </el-form-item>
                    <el-form-item label="结假时间：">
                        <div class="block">
                            <el-date-picker
                                v-model="release_time"
                                :picker-options="pickerOptions"
                                align="right"
                                placeholder="选择日期时间"
                                type="datetime">
                            </el-date-picker>
                        </div>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="leaveUpload">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "AskOfLeave",
    data() {
        return {
            dialogVisible: false,
            pickerOptions: {
                shortcuts: [{
                    text: '今天',
                    onClick(picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick(picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }]
            },
            radio: '病假',
            disease: '病假',
            routine: '事假',
            other: '其他',
            leave_time: '',
            release_time: ''
        };
    },
    methods: {
        leaveUpload() {
            axios.post("http://localhost:8080/api/leave",
                {
                    userId: this.$store.state.User.account,
                    username: this.$store.state.User.userName,
                    reason: this.radio,
                    leaveTime: this.commonDate(this.leave_time),
                    releaseTime: this.commonDate(this.release_time),
                }).then(res => {
                console.log(res.data.flag)
                this.dialogVisible = false;
                this.$store.dispatch("Holiday/getLeaveList",this.$store.state.User.account)
            });
        },
        commonDate(standardDate) {
            let date = new Date(standardDate)
            let y = date.getFullYear()
            let m = date.getMonth() + 1
            m = m < 10 ? ('0' + m) : m
            let d = date.getDate()
            d = d < 10 ? ('0' + d) : d
            
            let hh = date.getHours()
            hh = hh < 10 ? ('0' + hh) : hh;
            let mm = date.getMinutes()
            mm = mm < 10 ? ('0' + mm) : mm;
            let ss = date.getSeconds()
            ss = ss < 10 ? ('0' + ss) : ss;
            return y + '-' + m + '-' + d + ' ' + hh + ":" + mm + ":" + ss;
        },
    }
}
</script>

<style scoped>
.operation-box {
    padding: 15PX;
    text-align: right;
}
.el-dialog__header {
    background: #f6f8fa;
    border-bottom: solid darkgray 1px;
    border-radius: 5px 5px 0 0 ;
}
.el-dialog {
    border-radius: 5px;
}
.el-dialog__body{
    overflow: auto;
}
.el-dialog__footer{
    background: #f6f8fa;
    border-top: solid darkgray 1px;
    border-radius: 0 0 5px 5px;
}
</style>