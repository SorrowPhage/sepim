<template>
    <div>
        <div class="operation-box">
            <el-button type="primary" @click="dialogVisible = true">课程申请</el-button>
        </div>
        <div>
            <el-dialog
                :visible.sync="dialogVisible"
                title="课程申请"
                width="60%"
            >
                <el-form ref="form" label-width="100px">
                    <el-form-item label="选择课程：">
                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="学分：">
                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="选择教室：">
                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="选择时间：">
                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                                v-for="item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                            </el-option>
                        </el-select>
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
            options: [{
                value: '选项1',
                label: '黄金糕'
            }, {
                value: '选项2',
                label: '双皮奶'
            }, {
                value: '选项3',
                label: '蚵仔煎'
            }, {
                value: '选项4',
                label: '龙须面'
            }, {
                value: '选项5',
                label: '北京烤鸭'
            }],
            value: '',
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