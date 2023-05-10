<template>
    <div class="main">
        <div class="wrapper">
            <div class="row">
                <div class="col">
                    <section class="panel">
                        <div class="pane-body">
<!--                            <div class="nav-box">-->
<!--                            <el-menu-->
<!--                                default-active="2"-->
<!--                                class="el-menu-vertical-demo"-->
<!--                                >-->
<!--                                    <el-menu-item-group>-->
<!--                                        <template slot="title">分组一</template>-->
<!--                                        <el-menu-item index="1-1">ping</el-menu-item>-->
<!--                                        <el-menu-item index="1-2">选项2</el-menu-item>-->
<!--                                    </el-menu-item-group>-->
<!--                                    <el-menu-item-group title="分组2">-->
<!--                                        <el-menu-item index="1-3">选项3</el-menu-item>-->
<!--                                        <el-menu-item index="1-4">选项4</el-menu-item>-->
<!--                                    </el-menu-item-group>-->
<!--                            </el-menu>-->
<!--                            </div>-->
                            <div class="management-box">
                                <div>
                                    <el-button type="primary" @click="pingRedis">ping</el-button>
                                    <el-button type="primary" @click="setRedis">set</el-button>
                                    <el-button type="primary" @click="getRedis">get</el-button>
                                    <el-button type="primary" @click="setHaveTime">setHaveTime</el-button>
                                    <el-button type="primary" @click="hasKeyRedis">hasKey</el-button>
                                    <el-button type="primary" @click="incrRedis">incr</el-button>
                                    <el-button type="primary" @click="decrRedis">decr</el-button>
                                    <el-button type="primary" @click="deleteRedis">delete</el-button>
                                    
                                </div>
                                
                                <div class="sp-overview-box" ref="redis_context" v-html="pingresult">
                                
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
import axios from "axios";

export default {
    name: "SeckillContent",
    data(){
        return{
            pingresult:''
        }
    },
    methods:{
        pingRedis() {
            axios.post("http://localhost:8080/api/r/ping").then(res=>{
                if (this.pingresult !== '') {
                    this.pingresult = this.pingresult + "\n" + res.data.data;
                    this.$nextTick(()=>{
                        this.$refs.redis_context.scrollTop = this.$refs.redis_context.scrollHeight;
                    })
                } else {
                    this.pingresult =  res.data.data;
                }
                
            })
        },
        setRedis() {
            axios.post("http://localhost:8080/api/r/set").then(res=>{
                if (this.pingresult !== '') {
                    this.pingresult = this.pingresult + "\n" + res.data.data;
                    this.$nextTick(()=>{
                        this.$refs.redis_context.scrollTop = this.$refs.redis_context.scrollHeight;
                    })
                } else {
                    this.pingresult =  res.data.data;
                }
            
            })
        },
        getRedis(){
            axios.post("http://localhost:8080/api/r/get").then(res=>{
                if (this.pingresult !== '') {
                    this.pingresult = this.pingresult + "\n" + res.data.data;
                    this.$nextTick(()=>{
                        this.$refs.redis_context.scrollTop = this.$refs.redis_context.scrollHeight;
                    })
                } else {
                    this.pingresult =  res.data.data;
                }
                // console.log(res.data.data)
            })
        },
        setHaveTime() {
            axios.post("http://localhost:8080/api/r/setHaveTime").then(res=>{
                if (this.pingresult !== '') {
                    this.pingresult = this.pingresult + "\n" + res.data.data;
                    this.$nextTick(()=>{
                        this.$refs.redis_context.scrollTop = this.$refs.redis_context.scrollHeight;
                    })
                } else {
                    this.pingresult =  res.data.data;
                }
                // console.log(res.data.data)
            })
        },
        hasKeyRedis() {
            axios.post("http://localhost:8080/api/r/hasKey").then(res=>{
                if (this.pingresult !== '') {
                    this.pingresult = this.pingresult + "\n" + res.data.data;
                    this.$nextTick(()=>{
                        this.$refs.redis_context.scrollTop = this.$refs.redis_context.scrollHeight;
                    })
                } else {
                    this.pingresult =  res.data.data;
                }
                // console.log(res.data.data)
            })
        },
        incrRedis() {
            axios.post("http://localhost:8080/api/r/incr").then(res=>{
                if (this.pingresult !== '') {
                    this.pingresult = this.pingresult + "\n" + res.data.data;
                    this.$nextTick(()=>{
                        this.$refs.redis_context.scrollTop = this.$refs.redis_context.scrollHeight;
                    })
                } else {
                    this.pingresult =  res.data.data;
                }
                // console.log(res.data.data)
            })
        },
        decrRedis() {
            axios.post("http://localhost:8080/api/r/decr").then(res=>{
                if (this.pingresult !== '') {
                    this.pingresult = this.pingresult + "\n" + res.data.data;
                    this.$nextTick(()=>{
                        this.$refs.redis_context.scrollTop = this.$refs.redis_context.scrollHeight;
                    })
                } else {
                    this.pingresult =  res.data.data;
                }
                // console.log(res.data.data)
            })
        },
        deleteRedis() {
            axios.post("http://localhost:8080/api/r/delete").then(res=>{
                if (this.pingresult !== '') {
                    this.pingresult = this.pingresult + "\n" + res.data.data;
                    this.$nextTick(()=>{
                        this.$refs.redis_context.scrollTop = this.$refs.redis_context.scrollHeight;
                    })
                } else {
                    this.pingresult =  res.data.data;
                }
                // console.log(res.data.data)
            })
        },
    },
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
    /*-webkit-box-shadow: 0 1px 1px rgb(0 0 0 / 5%);*/
    /*box-shadow: 0 1px 1px rgb(0 0 0 / 5%);*/
}
.nav-box{
    width: 150px;
}
.management-box {
    position: absolute;
    left: 170px;
    top: 0;
}

.sp-overview-box {
    margin-top: 3px;
    height: 450px;
    width: 900px;
    overflow: auto;
    border-radius: 3px;
    border: 1px solid darkgray;
    padding: 5px;
    color:#80888c;
    font-size: 16px !important;
    white-space: pre-wrap;
}
</style>