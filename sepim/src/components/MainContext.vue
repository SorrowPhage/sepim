<template>
    <div class="main">
        <vue-particles
            :clickEffect="true"
            :hoverEffect="true"
            :lineLinked="true"
            :lineOpacity="0.4"
            :linesDistance="150"
            :linesWidth="1"
            :moveSpeed="3"
            :particleOpacity="0.7"
            :particleSize="4"
            :particlesNumber="100"
            class="login-bg"
            clickMode="push"
            color="#39AFFD"
            hoverMode="grab"
            linesColor="#8DD1FE"
            shapeType="circle"
        >
        </vue-particles>
        <div class="line-box">
            <el-row>
                <el-col :span="4" style="margin-top: 40px">
                    <el-row>
                        <el-col :span="24">
                            <span style="font-size: 10px">Latest Folder</span>
                        </el-col>
                    </el-row>
                    <el-row>
                        <FolderDisplay v-for="folder in activities" :key="folder.id" :des="folder.roughly"
                                       :file_id="folder.id" :name="$store.state.User.userName"
                                       :time="folder.time" :title="folder.title" :type="folder.type"
                                       :url="$store.state.User.avatarUrl"></FolderDisplay>
                    </el-row>
                </el-col>
                <el-col :span="16" style="border-left: 1px solid #eef5fc;">
                    <div id="mychart" :style="myChartStyle" class="echart"></div>
                </el-col>
                <el-col :span="4" style="margin-top: 40px">
                    <el-timeline :reverse="false">
                        <el-timeline-item v-for="(activity, index) in activities"
                                          :key="index"
                                          :timestamp="activity.time"
                                          placement="top">
                            {{ activity.title }}
                        </el-timeline-item>
                    </el-timeline>
                </el-col>
            </el-row>
        
        </div>
    
    </div>
</template>

<script>
import axios from "axios";
import {mapGetters} from "vuex";
import * as echarts from "echarts";
import FolderDisplay from "@/components/main/FolderDisplay";

export default {
    name: 'MainContext',
    components: {FolderDisplay},
    data() {
        return {
            recommend: [],
            list: [],
            myChart: {},
            showLine: false,
            // xData: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"], //横坐标
            xData: [], //横坐标
            // yData: [23, 24, 18, 25, 27, 28, 25], //人数数据
            yData: [], //人数数据
            myChartStyle: {float: "left", width: "100%", height: "400px"}, //图表样式
            activities: [],
            
        };
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
        initEcharts(x, y) {
            
            const option = {
                xAxis: {
                    data: x,
                    // axisLabel: { //设置x轴的字
                    //     show: true,
                    //     interval: 0,//使x轴横坐标全部显示
                    //     textStyle: {//x轴字体样式
                    //         color: "rgba(219,225,255,1)",
                    //         margin: 15
                    //     },
                    // }
                },
                yAxis: {},
                series: [
                    {
                        data: y,
                        type: "line" // 类型设置为折线图
                    }
                ],
                title: {
                    show: true,
                    text: "",
                    textStyle: {
                        // 主标题文本样式
                        fontFamily: "-apple-system,BlinkMacSystemFont,\"Segoe UI\",\"Noto Sans\",Helvetica,Arial,sans-serif,\"Apple Color Emoji\",\"Segoe UI Emoji\"",
                        fontSize: 24,
                        fontStyle: "normal",
                        color: "#333",
                    },
                },
            };
            this.myChart = echarts.init(document.getElementById("mychart"));
            this.myChart.setOption(option);
            //随着屏幕大小调节图表
            // window.addEventListener("resize", () => {
            //     this.myChart.resize();
            // });
        },
    },
    mounted() {
        axios.post("http://localhost:8080/api/md/list", {userId: this.$store.state.User.account}).then(res => {
            if (res.data.flag === "md_list_succeed") {
                this.list = res.data.data.reverse();
                this.activities = this.list.slice(0, 4);
                for (var i = 13; i >= 0; i--) {
                    var dd = new Date();
                    dd.setDate(dd.getDate() - i);
                    var y = dd.getFullYear();
                    var m = dd.getMonth() + 1 < 10 ? "0" + (dd.getMonth() + 1) : dd.getMonth() + 1;
                    var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
                    this.xData.push(y + "-" + m + "-" + d);
                }
                for (var j = 0; j < this.xData.length; j++) {
                    let arr = this.list.filter((el) => {
                        return this.xData[j] === el.time;
                    })
                    this.yData[j] = arr.length;
                }
                this.initEcharts(this.xData, this.yData);
            }
        });
        
    }
};
</script>

<style scoped>
.user-body {
    /* margin: 0;
    padding: 0;
    background: url("../assets/img/bg/eva1.jpg") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed; */
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
    background-image: linear-gradient(
        125deg,
        #7FFFD4,
        #87CEEB,
        #40E0D0,
        #DA70D6
    );
    
    background-size: 400%;
    animation: bganimation 15s infinite;
    position: fixed;
}

@keyframes bganimation {
    0% {
        background-position: 0% 50%;
    }
    
    50% {
        background-position: 100% 50%;
    }
    
    100% {
        background-position: 0% 50%;
    }
}

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
    /*width: 80%;*/
    display: flex;
}

.res-box {
    width: 20%;
    /*background: #227cf9;*/
    margin-top: 100px;
}

.line-box {
    /*width: 60%;*/
    /*text-align: center;*/
    /*background: white;*/
    position: absolute;
    top: 50px;
    /*left: 200px;*/
    width: 80%;
    left: 50%;
    right: 50%;
    transform: translate(-50%, 0);
}

/*.line-s{*/
/*    margin-left:100px;*/
/*    !*margin-top:100px;*!*/
/*}*/
.line-style {
    background-image: linear-gradient(
        125deg,
        #7FFFD4,
        #87CEEB,
        #40E0D0,
        #DA70D6
    );
    
    background-size: 400%;
    animation: bganimation 15s infinite;
    position: fixed;
}

@keyframes bganimation {
    0% {
        background-position: 0% 50%;
    }
    
    50% {
        background-position: 100% 50%;
    }
    
    100% {
        background-position: 0% 50%;
    }
}


.his-box {
    width: 20%;
}

.login {
    width: 100%;
    height: 100%;
    /*//color: #cccccc;*/
    /*如果想做背景图片 可以给标签一个class 直接添加背景图*/
    position: relative;
}
</style>