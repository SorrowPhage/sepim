<template>
    <div class="main">
        <el-button type="primary" @click="goWUzQi">五子棋</el-button>
    </div>
</template>

<script>
export default {
    name: "PhageGames",
    data() {
        return{
        
        }
    },
    methods:{
        goWUzQi() {
            this.$router.push({
                name:'wuziqi',
                params:{
                    account: this.$store.state.Detail.account,
                }
            })
        },
    }
}
</script>

<style scoped>
* {
    box-sizing: border-box;
}

.main {
    margin-top: 50px;
    /*background-color: rgb(228, 228, 228);*/
    /*height: 100%;*/
    /*overflow: auto;*/
    /*background-image: linear-gradient(120deg, #155799, #159957);*/
    height: 340px;
    display: flex;
    justify-content: center;
    /*align-items: center;*/
}
</style>