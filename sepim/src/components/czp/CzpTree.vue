<template>
    <div class="tree">
        <el-row style="z-index: 1000">
            <el-col :span="24" class="el-col-bar">
<!--                <el-button @click="exportData()" type="success" size="mini" style="float: right; margin: 0 10px 0 10px;">addczptree</el-button>-->
                <el-button type="primary" size="mini" @click="loadData" style="float: right; margin: 0 10px 0 10px;">查询（不包括分支）</el-button>
                <el-button type="primary" size="mini" @click="loadData2All" style="float: right; margin: 0 10px 0 10px;">查询全部</el-button>
                <el-input v-model="user.smallGroup" placeholder="zuqun" size="mini"  style="float: right; margin: 0 10px 0 10px;" ></el-input>
                <el-button type="primary" size="mini" @click="getDataByUserId" style="float: right; margin: 0 10px 0 10px;">查询全部(用户)</el-button>
                <el-input v-model="user.userId" placeholder="userId" size="mini"  style="float: right; margin: 0 10px 0 10px;" ></el-input>
<!--                <el-input v-model="user.areaName" placeholder="地区" size="mini"  style="float: right; margin: 0 10px 0 10px;" ></el-input>-->
            
            </el-col>
        </el-row>
        
        
        <el-row>
<!--            <el-col :span="12" >-->
<!--                <div style="border:1px solid #48A3F0;width: 100%;height: 500px;margin: auto;position:relative;overflow: hidden">-->
<!--                    <vue-draggable-resizable-->
<!--                        w="auto"-->
<!--                        h="auto"-->
<!--                        :x="0"-->
<!--                        :y="0"-->
<!--                        :resizable="false"-->
<!--                        @resizing="onResize">-->
<!--                        <vue2-org-tree :data="data"  :render-content="renderContent" />-->
<!--                        -->
<!--                    </vue-draggable-resizable>-->
<!--                </div>-->
<!--            </el-col>-->
            <el-col :span="24">
                <div style="border:1px solid #48A3F0;width: 100%;height: 500px;margin: auto;position:relative;overflow: hidden">
<!--                    <div class="treeDiv"  ref="czpTree" v-drag>-->
<!--                        <TreeChat :json="data"></TreeChat>-->
<!--                    </div>-->
                    <vue-draggable-resizable
                        w="auto"
                        h="auto"
                        :x="0"
                        :y="0"
                        :resizable="false"
                        @resizing="onResize"
                    >
                        
                        <TreeChat :json="data"  ref="czpTree" @wheel.prevent="handleTableWheel($event)" ></TreeChat>
                        
                    </vue-draggable-resizable>
                </div>
                
            </el-col>
        </el-row>

    </div>
</template>

<script>
import 'vue2-org-tree/dist/style.css';
import TreeChat from "@/components/czp/TreeChat";
import axios from "axios";
export default {
    name: "CzpTree",
    components: {TreeChat},
    data() {
        return {
            user: {
                areaName: '',
                smallGroup: '',
                userId: '',
            },
            data: {},
        };
    },
    directives: {
        drag(el) {
            let oDiv = el // 当前元素
            // let self = this // 上下文
            // 禁止选择网页上的文字
            document.onselectstart = function () {
                return false
            }
            oDiv.onmousedown = function (e) {
                // 鼠标按下，计算当前元素距离可视区的距离
                let disX = e.clientX - oDiv.offsetLeft
                let disY = e.clientY - oDiv.offsetTop
                document.onmousemove = function (e) {
                    // 通过事件委托，计算移动的距离
                    let l = e.clientX - disX
                    let t = e.clientY - disY
                    // 移动当前元素
                    oDiv.style.left = l + 'px'
                    oDiv.style.top = t + 'px'
                }
                document.onmouseup = function (e) {
                    document.onmousemove = null
                    document.onmouseup = null
                }
                // return false不加的话可能导致黏连，就是拖到一个地方时div粘在鼠标上不下来，相当于onmouseup失效
                return false
            }
        }
    },
    methods:{
        loadData() {
            axios.post("http://localhost:8080/api/czp/rc",this.user).then(res=>{
                this.data = res.data.data;
            })
        },
        loadData2All() {
            axios.post("http://localhost:8080/api/czp/all_rc",this.user).then(res=>{
                this.data = res.data.data;
            })
        },
        getDataByUserId() {
            axios.get("http://localhost:8080/api/czp/rc_userid",{params: this.user}).then(res=>{
                this.data = res.data.data;
            })
        },
        renderContent(h, data) {
            if (data.sex === '1') {
                console.log(data.sex)
                return (
                    <div style="color: #227cf9;">
                        <div>
                            <span>{data.name}</span>
                        </div>
                    </div>
                );
            } else {
                console.log(data.sex)
                return (
                    <div  style="color: #f922f5;">
                        <div>
                            <span>{data.name}</span>
                        </div>
                    </div>
                );
            }
        },
        handleTableWheel(event) {
            let obj = this.$refs.czpTree;
            return this.tableZoom(obj, event);
        },
        tableZoom(obj, event) {
            // 一开始默认是100%
            let zoom = parseInt(obj.style.zoom, 10) || 100;
            // 滚轮滚一下wheelDelta的值增加或减少120
            zoom += event.wheelDelta / 12;
            if (zoom > 0) {
                obj.style.zoom = zoom + "%";
            }
            return false;
        },
        onResize: function (x, y, width, height) {
            this.x = x
            this.y = y
            this.width = width
            this.height = height
        },

    }
}
</script>

<style scoped>
.tree{
    margin-top: 50px;
}
.treeDiv::-webkit-scrollbar {
    display: none
}

.el-col-bar .el-input {
    width: auto;
}

.el-col-bar {
    padding: 10px 20px;
    height: 50px;
    border-bottom: 2px solid #48A3F0;
    text-align: right;
}

.treeDiv {
    z-index: 999;
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 5px;
    
    background: rgba(246, 248, 250, 0.33);
    position: fixed;
    /*left: 0;*/
    /*top: 0;*/
    font-size: 2rem;
}
.no-select {
    user-select: none;
}


</style>