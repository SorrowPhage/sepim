<template>
    <div style="display: flex">
        <div style="width: 20%">
            <el-menu
                default-active="/index.html/detail/follow/phages"
                class="el-menu-vertical-demo"
                :router="true">
                <el-menu-item index="/index.html/detail/follow/phages" :route="{name:'phages',query:{userId:this.$route.query.account}}">
                    <i class="el-icon-setting"></i>
                    <span slot="title">PHAGES</span>
                </el-menu-item>
                <el-menu-item index="/index.html/detail/follow/erc" :route="{name:'erc',query:{userId:this.$route.query.account}}">
                    <i class="el-icon-setting"></i>
                    <span slot="title">ERC</span>
                </el-menu-item>
            </el-menu>
        </div>
        <div style="width: 80%;padding: 5px">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
export default {
    name: "UserFollow"
}
</script>

<style scoped>

</style>