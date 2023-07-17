<template>
    <div style="margin-top: 50px;">
        <el-container>
            <el-aside width="200px">
                <el-menu class="el-menu-vertical-demo" default-active="1-4-1"
                         @select="handleSelect" >
                    <el-menu-item index="3">
                        <i class="el-icon-s-fold"></i>
                        <span slot="title">Home</span>
                    </el-menu-item>
                    <el-submenu index="1">
                        <template slot="title">
                            <i class="el-icon-s-order"></i>
                            <span slot="title">统计图</span>
                        </template>
                        <el-menu-item index="1-1">
                            <span slot="title">折线图</span>
                        </el-menu-item>
                    </el-submenu>
<!--                    <el-submenu index="2">-->
<!--                        <template slot="title">-->
<!--                            <i class="el-icon-s-order"></i>-->
<!--                            <span slot="title">预测</span>-->
<!--                        </template>-->
<!--                        <el-menu-item index="2-1">-->
<!--                            <span slot="title">预测</span>-->
<!--                        </el-menu-item>-->
<!--                    </el-submenu>-->
                </el-menu>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import * as echarts from "echarts";
import axios from "axios";

export default {
    name: "UserDataAnalysis",
    data() {
        return {
        
        };
    },
    methods: {
        handleSelect(key) {
            if (key === "1-1") {
                this.$router.push({name: 'analysis_line'})
            }else if (key === "2-1") {
                this.$router.push({name: 'rediction'})
            }else if (key === '3') {
                this.$router.push({name:'analysis_index'})
            }
        },
    }
    
}
</script>

<style scoped>

</style>