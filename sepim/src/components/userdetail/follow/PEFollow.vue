<template>
    <div class="sp-user-info-box" @click="goOverview">
        <div class="flex-shrink-0 mr-2">
            <el-avatar :size="50" :src="avatarUrl"></el-avatar>
            <!--            <el-avatar :size="20" :src="'http://localhost:8088/upload/sepim/avatar/default.png'"></el-avatar>-->
        </div>
        <div class="flex-auto">
            <div class="d-flex">
                <div class="f4 text-normal">
<!--                    <router-link :to="{name:'detail',query:{account:account}}">{{ username }}</router-link>-->
                    {{username}}
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "PEFollow",
    props: ['avatarUrl', 'username', 'account'],
    methods:{
        goOverview() {
            this.$router.push({
                name: 'detail',
                query:{
                    account:this.account
                }
            })
        },
    }
}
</script>

<style scoped>
.sp-user-info-box {
    width: 100%;
    /*width: 150px;*/
    display: flex;
    height: 51px;
    border: 1px solid #d8dee4;
    padding: 16px;
    margin: 3px;
}
.sp-user-info-box:hover{
    cursor: pointer;
    background: #9acbff;
}
.flex-shrink-0 {
    flex-shrink: 0 !important;
}
.mr-2{
    margin-right: 8px !important;
}
.flex-auto {
    flex: auto !important;
}
.d-flex {
    display: flex !important;
}
.f4 {
    font-size: 16px !important;
}
.text-normal {
    font-weight: 400 !important;
    color: #218bff;
}
a{
    color: #0969da;
    text-decoration: none;
}
a:hover{
    color: orangered;
}
</style>