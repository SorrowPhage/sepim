<template>
    <div class="body">
        <div class="register-box">
            <div class="main-content-box">
                <h1>找回密码</h1>
                <div class="step-box">
                    <el-steps :active="active" finish-status="success" align-center>
                        <el-step title="步骤1" description="验证身份"></el-step>
                        <el-step title="步骤2" description="更改密码"></el-step>
                    </el-steps>
                </div>
                <div v-if="isFirst">
                    <div class="sp-input-box">
                        <input class="sp-input-intact" v-model="user.id" type="text" placeholder="账号"/>
                    </div>
                    <div class="sp-input-box">
                        <input class="sp-input-intact" v-model="user.email" type="text" placeholder="邮箱"/>
                    </div>
                    <div class="sp-input-box">
                        <input class="sp-input-verify" v-model="user.verCode" type="text" placeholder="验证码"/>
                        <button v-if="display_button" class="sp-button-get_verify_code" @click="sendVerCode">获取验证码</button>
                        <button v-if="!display_button" class="sp-button-get_verify_code_vanish">{{timer}}秒后重试</button>
                    </div>
                    <div class="sp-input-box">
                        <button class="sp-button-register" @click="verify_status">
                            验证身份
                        </button>
                    </div>
                </div>
                <div v-if="!isFirst">
                    <div class="sp-input-box">
                        <input
                                class="sp-input-intact"
                                type="password"
                                placeholder="新密码"
                                v-model="user.password"
                        />
                    </div>
                    <div class="sp-input-box">
                        <input
                                class="sp-input-intact"
                                type="password"
                                placeholder="确认密码"
                        />
                    </div>
                    <div class="sp-input-box">
                        <button class="sp-button-register" @click="retrieve_password">
                            更改密码
                        </button>
                    </div>
                </div>
            </div>
            <div class="return-login">
                <router-link to="/login">登录</router-link>
            </div>
        </div>
    </div>
</template>


<script>
    import axios from "axios";

    export default {
        name: "UserRegister",
        data() {
            return {
                display_button: true,
                active: 0,
                isFirst: true,
                user:{
                    id: '',
                    email: '',
                    'verCode':'',
                    password:''
                },
                message:{
                    id:'',
                    email:'',
                },
                timer: 0
            };
        },
        methods: {
            verify_status() {
                axios.post('http://localhost:8080/api/retrieve',this.user).then(
                    response=>{
                        if (response.data.flag == "ver_defeat") {
                        //    验证码错误
                            console.log("验证码错误");
                            return;
                        }else if (response.data.flag == "ver_status_defeat") {
                            //    身份验证失败
                            console.log("身份验证失败");
                        } else {
                            this.isFirst = false;
                            this.active = this.active + 1;
                        }
                })
            },
            retrieve_password() {
                axios.post("http://localhost:8080/api/change", this.user).then(
                    response=>{
                        if (response.data.flag == "succeed") {
                            console.log("成功");
                        }
                    }
                );
                this.active = this.active + 1;
            },
            sendVerCode() {
                if (this.user.email == '') {
                    this.message.email = '邮箱不能为空';
                    return;
                } else if (this.message.email == '') {
                    axios.post('http://localhost:8080/api/sendcode', {tos: this.user.email, subject: '找回密码'});
                    this.display_button = false;
                    this.timer = 60;
                    let timeInt = setInterval(() => {
                        this.timer--;
                        if (this.timer <= 0) {
                            this.display_button = true;
                            clearInterval(timeInt);
                        }
                    }, 1000);
                }
            },
        },
    };
</script>

<style scoped>
    .body {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        background-image: linear-gradient(
                125deg,
                #2c3e50,
                #27ae60,
                #2980b9,
                #eb4d4b,
                #2c3e50
        );
        background-size: 400%;
        animation: bganimation 15s infinite;
        position: fixed;
    }

    @keyframes bganimation {
        0% {
            background-position: 0% 50%;
        }

        50% {
            background-position: 100% 50%;
        }

        100% {
            background-position: 0% 50%;
        }
    }

    .register-box {
        width: 300px;
        height: 400px;
        background-color: white;
        border: 10px;
        padding: 20px 50px;
        border-radius: 20px;
    }

    .main-content-box {
        width: 300px;
    }

    .main-content-box h1 {
        text-align: center;
    }

    .sp-input-box {
        width: 300px;
        margin-bottom: 15px;
        height: 25px;
    }

    .sp-input-intact {
        width: 280px;
        height: 30px;
        margin-left: 10px;
        border-radius: 5px;
        border: 1px solid gray;
        outline: none;
    }

    .sp-input-intact:focus-within {
        border-color: #3083ff;
    }

    .sp-input-verify {
        width: 84px;
        height: 30px;
        margin-left: 10px;
        border-radius: 5px;
        border: 1px solid gray;
        outline: none;
    }

    .sp-input-verify:focus-within {
        border: 1px solid #3083ff;
    }

    .sp-button-get_verify_code {
        color: white;
        width: 186px;
        height: 30px;
        margin-left: 10px;
        border-radius: 5px;
        border: 1px solid #3083ff;
        background-image: linear-gradient(0deg, #398bff, #3083ff);
    }

    .sp-button-get_verify_code:hover {
        cursor: pointer;
        background-image: linear-gradient(0deg, #227cf9, #3083ff)
    }

    .sp-button-register {
        color: white;
        margin-left: 10px;
        width: 288px;
        height: 30px;
        border-radius: 5px;
        border: 1px solid #3083ff;
        background-image: linear-gradient(0deg, #398bff, #3083ff);
    }

    .sp-button-register:hover {
        cursor: pointer;
        background-image: linear-gradient(0deg, #227cf9, #3083ff)
    }

    .sp-button-get_verify_code_vanish {
        color: white;
        width: 186px;
        height: 30px;
        margin-left: 10px;
        border-radius: 5px;
        border: 1px solid #bac4d2;
        background-image: linear-gradient(0deg, #bac4d2, #b6bcc6);
    }

    .sp-button-get_verify_code_vanish:hover {
        cursor: not-allowed;
    }

    .return-login {
        width: 300px;
        margin-top: 80px;
    }

    .return-login a {
        font-size: 20px;
        color: #3498db;
        text-decoration: none;
    }
</style>