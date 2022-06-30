<template>
    <div class="sp-user-info-box">
        <div class="flex-shrink-0 mr-2">
            <el-avatar :size="20" :src="avatarUrl"></el-avatar>
<!--            <el-avatar :size="20" :src="'http://localhost:8088/upload/sepim/avatar/default.png'"></el-avatar>-->
        </div>
        <div class="flex-auto">
            <div class="d-flex">
                <div class="f4 text-normal">
                    <router-link :to="{name:'detail',query:{account:account}}">{{ username }}</router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "UserList",
    props: ['avatarUrl', 'username', 'account']
}
</script>

<style scoped>
.sp-user-info-box{
    width: 100%;
    display: flex;
    height: 71px;
    border-color: #d8dee4;
    border-top-style: solid;
    border-top-width: 1px;
    padding:  16px 0;
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