<template>
    <div class="header">
        <!-- <button class="toggle-btn" @click="changeCollapse">切换</button> -->
        <div class="menu-right">
            <ul class="notification-menu">
                <li>
                    <label ref="showUl" class="btn btn-default dropdown-toggle" @click="isTrue=!isTrue">
                        <img :src="avatar_url"/>
                        {{ userName }}
                        <span class="caret"></span>
                    </label>
                    <!-- 有问题,隐藏 -->
                    <ul v-show="isTrue" ref="myUl" class="dropdown-menu dropdown-menu-usermenu pull-right">
                        <li @click="goIndex">
                            <i class="el-icon-user-solid"></i> 主页
                        </li>
                        <li @click="checkUserInfo">
                            <i class="el-icon-user-solid"></i> 个人信息
                        </li>
                        <li @click="setPassword">
                            <i class="el-icon-setting"></i> 修改密码
                        </li>
                        <li @click="logOut">
                            <i class="el-icon-right"></i> 退出
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import {mapMutations, mapState, mapGetters} from "vuex";
import {sessionReplaceStore} from "@/utils/session_util"

export default {
    name: "AdminHeader",
    data() {
        return {
            isTrue: false,
        };
    },
    computed: {
        ...mapState("User", ["userName"]),
        ...mapGetters("User", ["avatar_url"]),
    },
    methods: {
        ...mapMutations("Menu", ["changeCollapse"]),
        goIndex() {
            this.isTrue = false;
            this.$router.push({
                name:'main'
            })
        },
        checkUserInfo() {
            this.isTrue = false;
            this.$router.push({
                name: 'userinfo',
            });
        },
        setPassword() {
            this.isTrue = false;
            this.$router.push({
                name: 'setpassword',
            })
        },
        logOut() {
            this.isTrue = false;
            localStorage.removeItem("token");
            this.$router.push({
                name: "login"
            });
        },
        showUl(e) {
            //需要使用v-show绑定
            if (!(this.$refs.myUl.contains(e.target) || this.$refs.showUl.contains(e.target))) this.isTrue = false;
        }
        
    },
    created() {
        // 页面刷新时恢复store数据
        sessionReplaceStore('store');
        // 发生页面销毁事件前将store数据存储至sessionStoreage中
        window.addEventListener('beforeunload', () => {
            sessionStorage.setItem('store', JSON.stringify(this.$store.state))
        });
    },
    mounted() {
        document.addEventListener('click', this.showUl);
    },
    destroyed() {
        document.removeEventListener('click', this.showUl);
    }
};
</script>

<style scoped>
.header {
    position: fixed;
    left: 240px;
    width: 100%;
    background-color: white;
    top: 0;
    height: 50px;
    z-index: 100;
}

.toggle-btn {
    outline: none;
    margin-top: 0px;
    width: 50px;
    height: 50px;
    /* font-size: 20px; */
    /* padding: 15px; */
    cursor: pointer;
    /* float: left; */
    background-color: white;
    border: white;
}

.notification-menu {
    list-style: none;
}

.notification-menu > li {
    display: inline-block;
    float: left;
    position: relative;
}

.menu-right {
    float: right;
    margin-right: 15px;
    margin-right: 255px;
    margin-top: 0;
    height: 50px;
}

/*会影响导航栏*/
ul {
    margin-top: 0;
    margin-bottom: 10px;
}

ul {
    display: block;
}

a {
    text-decoration: none;
    margin-top: 0;
}

* {
    box-sizing: border-box;
}

li {
    display: list-item;
    text-align: -webkit-match-parent;
}

.btn {
    display: inline-block;
    margin-bottom: 0;
    font-weight: 400;
    text-align: center;
    vertical-align: middle;
    cursor: pointer;
    background-image: none;
    border: 1px solid transparent;
    white-space: nowrap;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.42857143;
    border-radius: 4px;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}

.btn-default {
    color: #333;
    background-color: #fff;
    border-color: #ccc;
}

.dropdown-toggle {
    margin-top: 0;
    padding: 12px 10px;
    border-color: #fff;
    background: white;
    -moz-border-radius: 0;
    -webkit-border-radius: 0;
    border-radius: 0;
    border: none;
}

.dropdown-toggle img {
    vertical-align: middle;
    margin-right: 5px;
    width: 26px;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
}

/* 鼠标悬浮改变头像背景颜色 */
.dropdown-toggle:hover {
    background-color: #409EFF;
}

.dropdown-menu {
    display: block;
    position: absolute;
    top: 100%;
    border: 0;
    margin-top: 0px;
    -moz-border-radius: 2px 0 2px 2px;
    -webkit-border-radius: 2px 0 2px 2px;
    border-radius: 2px 0 2px 2px;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
    box-shadow: none;
    padding: 5px;
    margin: 2px 0 0;
    list-style: none;
    font-size: 14px;
}

.dropdown-menu:after {
    border-bottom: 6px solid #409EFF;
    border-left: 6px solid transparent;
    border-right: 6px solid transparent;
    content: "";
    display: inline-block;
    right: 10px;
    position: absolute;
    top: -6px;
}

.dropdown-menu-usermenu {
    background: #409EFF;
    min-width: 200px;
    color: white;
}

.pull-right {
    right: 0;
    left: auto;
    width: 100%;
}

.pull-right li:hover {
    background: white;
    cursor: pointer;
    color: black;
}

.notification-menu .dropdown-toggle .caret {
    margin-left: 5px;
}

.btn .caret {
    margin-left: 0;
}

.caret {
    display: inline-block;
    width: 0;
    height: 0;
    margin-left: 2px;
    vertical-align: middle;
    border-top: 4px solid;
    border-right: 4px solid transparent;
    border-left: 4px solid transparent;
}
</style>