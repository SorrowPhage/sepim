<template>
    <div class="home">
<!--        <nav-menu></nav-menu>-->
        <div class="main-content">
            <admin-header></admin-header>
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    /*import NavMenu from "../components/NavMenu.vue";
    import AdminHeader from "../components/AdminHeader.vue";
    export default {
        name: "HomePage",
        components: {NavMenu, AdminHeader},
    };*/
    import AdminHeader from "../components/AdminHeader.vue";
    export default {
        name: "HomePage",
        components: {AdminHeader},
    };
</script>

<style>
    .main-content {
        /*margin-left: 240px;*/
        /*background: white;*/
        /*background-repeat: no-repeat;*/
        /*background-position: center 40px;*/
        /*background-image: url("~@/assets/img/user_bg/3-3.jpg");*/
        /*background-size:cover;*/
        /*background-attachment: fixed*/
    }

    ::-webkit-scrollbar {
        width: 4px;
        height: 9px;
    }

    ::-webkit-scrollbar-thumb {
        /*border-radius: 7px;*/
        background-color: rgb(2, 109, 224);
    }
</style>