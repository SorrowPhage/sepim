<template>
    <el-row>
        <el-col :span="24">
            <div id="mychart" :style="myChartStyle" class="echart"></div>
        </el-col>
    </el-row>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";

export default {
    name: "FolderCreateLine",
    data() {
        return {
            xData: [], //横坐标
            yData: [], //人数数据
            myChartStyle: {float: "left", width: "100%", height: "400px"}, //图表样式
        }
    },
    methods: {
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
        },
    },
    mounted() {
        axios.post("http://localhost:8080/api/md/list", {userId: this.$store.state.User.account}).then(res => {
            if (res.data.flag === "md_list_succeed") {
                this.list = res.data.data;
                for (let i = 0; i < this.list.length; i++) {
                    this.xData.push(this.list[i].time);
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
}
</script>

<style scoped>

</style>