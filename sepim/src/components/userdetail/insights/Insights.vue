<template>
    <div>
        <div id="loginChart"  style="width: 800px;height: 300px;" class="echart"></div>
    </div>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";
export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: "Insights",
    data() {
        return {
            loginChart: {},
            xData: [], //横坐标
            yData: [], //创建文件数据,
            y2Data: [],//登录数据
            list: [],
            logins: [],
        };
    },
    methods: {
        initEchartsLoginData(x,y1,y2) {
            const option = {
                title: {
                    text: 'o.O ꒰ ˶• ༝ •˶꒱ Line'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['Logins','Creates']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                // toolbox: {
                //     feature: {
                //         saveAsImage: {}
                //     }
                // },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: x,
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: 'Logins',
                        type: 'line',
                        data: y2,
                    },
                    {
                        name: 'Creates',
                        type: 'line',
                        data: y1,
                    },
                    
            
                ]
            };
            this.loginChart = echarts.init((document.getElementById("loginChart")));
            this.loginChart.setOption(option);
        },
        loadEchartsData() {
            axios.post("http://localhost:8080/api/md/echarts-data",{userId: this.$route.query.account}).then(res=>{
                if (res.data.flag === "200") {
                    this.list = res.data.data.folderList;
                    this.logins = res.data.data.loginList;
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
                        });
                        this.yData[j] = arr.length;
                    
                        let arr2 = this.logins.filter((el) => {
                            return this.xData[j] === el.time.substring(0, 10);
                        });
                        this.y2Data[j] = arr2.length;
                    }
                    this.initEchartsLoginData(this.xData, this.yData, this.y2Data);
                }
            })
        },
    },
    mounted() {
        this.loadEchartsData();
    }
}
</script>

<style scoped>

</style>