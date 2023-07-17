<template>
    <div style="margin-top: 10px;border-bottom: 1px solid #eef5fc">
        <el-popover
            placement="top-start"
            width="300"
            trigger="hover"
            >
            <div>
                <el-row>
                    <el-col :span="3">
                        <i class="el-icon-folder"></i>
                    </el-col>
                    <el-col :span="21">
                        {{name}}/{{title}}<span class="Label Label--secondary  v-align-middle ml-1 mb-1">{{ type }}</span>
                    </el-col>
                </el-row>
                <el-row>
                    {{des}}
                </el-row>
                <el-row>
                    {{time}}
                </el-row>
            </div>
            <div slot="reference">
                <el-avatar src="http://localhost:8088/upload/sepim/avatar/963a0141622c4f999048a10d345d0b5cfile.png" :size="15"></el-avatar>
                <span class="folder_name" @click="readMd(file_id)">{{name}}/{{title}}</span>
            </div>
        </el-popover>
    </div>
</template>

<script>
export default {
    name: "FolderDisplay",
    props: ['title','name','file_id','url','type','des','time'],
    methods:{
        readMd(id) {
            this.$router.push({
                name: 'read',
                query:{
                    id: id,
                }
            })
        },
    }
}
</script>

<style scoped>
.folder_name {
    font-size: 5px;
    height: 100%;
}
.folder_name:hover{
    cursor: pointer;
    color: #227cf9;
    text-decoration: underline;
}
.Label {
    display: inline-block;
    font-size: 12px;
    font-weight: 500;
    line-height: 18px;
    white-space: nowrap;
    padding: 0px 7px;
    border-width: 1px;
    border-style: solid;
    border-image: initial;
    border-radius: 2em;
    border-color: #d0d7de;
}

.v-align-middle {
    vertical-align: middle !important;
}

.ml-1 {
    margin-left: 4px !important;
}

.mb-1 {
    margin-bottom: 4px !important;
}

.Label--secondary {
    color: #57606a;
    border-color: #d0d7de;
}
</style>