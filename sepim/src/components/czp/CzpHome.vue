<template>
    <div style="margin-top: 50px">
        <el-button @click="goCzpTree()">czptree</el-button>
        <el-button @click="goAddCzpTree()">addCzpTree</el-button>
    </div>
</template>

<script>
export default {
    name: "CzpHome",
    data() {
        return {

        };
    },
    methods: {
        goCzpTree() {
            this.$router.push({
                name:'czpt'
            });
        },
        goAddCzpTree() {
            this.$router.push({
                name:'add_czp'
            })
        },
    },
}
</script>

<style scoped>

</style>