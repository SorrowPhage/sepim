<template>
    <div v-loading.fullscreen.lock="loading">
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/index.html/analysis/predict' }">预测</el-breadcrumb-item>
            <el-breadcrumb-item><a href="/">活动管理</a></el-breadcrumb-item>
        </el-breadcrumb>
        <div v-html="html"></div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "SurveyCSV",
    data() {
        return {
            loading: true,
            idarr: [],
            pricearr: [],
            html: '',
        }
    },
    mounted() {
        // axios.get("http://localhost:8080/api/file/csv",{params:{id: this.$route.query.id}}).then(res=>{
        //     if (res.data.flag === "200") {
        //         console.log(res.data.data);
        //     }
        // })
        axios.get("http://localhost:8080/api/file/csv",{params:{id: this.$route.query.id}}).then(res=>{
            let data = res.data.data;
            
            //太卡了，不做了
            
            // let chartsstr = data.replace(/\n/g, ',');
            // chartsstr = chartsstr.replace(/\r/g, "");
            // //字符串文件以，分隔并转为数组，chartcvsmsg自定义空列表
            // let chartcvsmsg = chartsstr.split(',');
            // this.html = '<table>';
            // for (let i = 0; i < chartcvsmsg.length; i++) {
            //     if (i % 2 == 0) {
            //         this.idarr.push(chartcvsmsg[i]);
            //         this.html += "<tr><td>" + chartsstr[i] + "</td>";
            //     } else {
            //         this.pricearr.push(chartcvsmsg[i]);
            //         this.html += "<td>" + chartsstr[i] + "</td></tr>"
            //     }
            // }
            // this.html += "</table>";
            // this.loading = false;
        })
        
    }
}
</script>

<style scoped>
td {
    width: 150px;
    height: 30px;
    background: #227cf9;
    color: white;
}
</style>