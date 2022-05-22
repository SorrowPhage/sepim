<template>
    <div class="home">
        <nav-menu></nav-menu>
        <div class="main-content">
            <admin-header></admin-header>
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    import NavMenu from "../components/NavMenu.vue";
    import AdminHeader from "../components/AdminHeader.vue";

    export default {
        name: "HomePage",
        components: {NavMenu, AdminHeader},
    };
</script>

<style>
    .main-content {
        margin-left: 240px;
        background: white;
    }

    ::-webkit-scrollbar {
        width: 4px;
    }

    ::-webkit-scrollbar-thumb {
        border-radius: 7px;
        background-color: rgb(101, 206, 167);
    }
</style>