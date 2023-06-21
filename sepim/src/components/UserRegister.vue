<template>
    <div class="body">
        <div class="register-box">
            <div class="main-content-box">
                <h1>注册</h1>
                <div v-if="isFirst">
                    <div class="sp-input-box">
                        <input class="sp-input-intact" v-model="userInfo.username" @focus="message.username=''"
                               @blur="validationAccount" type="text" placeholder="用户名"/>
                        <span v-if="message.username"><i class="el-icon-info"></i>{{message.username}}</span>
                    </div>
                    <div class="sp-input-box">
                        <input class="sp-input-intact" v-model="userInfo.password" @focus="message.password=''"
                               @blur="validationPassword" type="password" placeholder="密码"/>
                        <span v-if="message.password"><i class="el-icon-info"></i>{{message.password}}</span>
                    </div>
                    <div class="sp-input-box">
                        <input ref="email" class="sp-input-intact" v-model="userInfo.email" @focus="message.email=''"
                               @blur="validationEmail" type="text" placeholder="邮箱"/>
                        <span v-if="message.email"><i class="el-icon-info"></i>{{message.email}}</span>
                    </div>
                    <div class="sp-input-box">
                        <input class="sp-input-verify" v-model="userInfo.verCode" @focus="message.verCode=''" @blur="validationVerCode" type="text" placeholder="验证码"/>
                        <button v-if="display_button" class="sp-button-get_verify_code" @click="sendVerCode">获取验证码
                        </button>
                        <button v-if="!display_button" class="sp-button-get_verify_code_vanish">{{timer}}秒后重试</button>
                        <span v-if="message.verCode"><i class="el-icon-info"></i>{{message.verCode}}</span>
                    </div>
                    <div class="sp-input-box">
                        <button class="sp-button-register" @click="regsiter">注册</button>
                    </div>
                </div>
                <div v-if="!isFirst">
                    <div class="sp-input-box">
                        <span class="result-text">您的账号：{{account}}</span><br>
<!--                        <span class="result-text">您的密码：{{userInfo.password}}</span>-->
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
    import axios from 'axios'

    export default {
        name: "UserRegister",
        data() {
            return {
                display_button: true,
                timer: 0,
                isFirst: true,
                userInfo: {
                    username: '',
                    password: '',
                    email: '',
                    'verCode': '',
                },
                message: {
                    username: '',
                    password: '',
                    email: '',
                    'verCode':'',
                },
                account: ''
            };
        },
        methods: {
            sendVerCode() {
                if (this.userInfo.email === '') {
                    this.message.email = '邮箱不能为空';
                    return;
                } else if (this.message.email === '') {
                    axios.post('http://localhost:8080/api/sendcode', {tos: this.userInfo.email, subject: '注册'});
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
            regsiter() {
                if (this.userInfo.username === '') {
                    this.message.account = '用户名不能为空'
                    return;
                }else if (this.userInfo.password === '') {
                    this.message.password = '密码不能为空';
                    return;
                }else if (this.userInfo.email === '') {
                    this.message.email = '邮箱不能为空';
                    return;
                }else if (this.userInfo.vercode === '') {
                    this.message.verCode = '验证码不能为空';
                    return;
                } else if (this.message.username === '' && this.message.password === '' && this.message.email === '') {
                    axios.post('http://localhost:8080/api/enrol', this.userInfo).then(response => {
                        if (response.data.flag === 'ver_defeat') {
                            this.message.verCode = '验证码错误';
                        } else {
                            this.isFirst = false;
                            this.account = response.data.data.id;
                        }
                    });
                }
            },
            validationAccount() {
                if (this.userInfo.username === '' || this.userInfo.username === undefined) {
                    this.message.account = "用户名不能为空";
                }
            },
            validationPassword() {
                if (this.userInfo.password == '' || this.userInfo.password == undefined || this.userInfo.password == null) {
                    this.message.password = "密码不能为空";
                } else if (this.userInfo.password.length < 6) {
                    this.message.password = "密码长度大于6";
                }
            },
            validationEmail() {
                if (this.userInfo.email == '' || this.userInfo.email == undefined || this.userInfo.email == null) {
                    this.message.email = "邮箱不能为空";
                } else {
                    let email_val = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                    if (!email_val.test(this.userInfo.email)) {
                        this.message.email = "请输入正确的邮箱";
                    }
                }
            },
            validationVerCode(){
                if (this.userInfo.verCode== '' || this.userInfo.verCode == undefined || this.userInfo.verCode == null) {
                    this.message.verCode = "验证码不能为空";
                } else if (this.userInfo.verCode.length != 6) {
                    this.message.verCode = "验证码长度为6";
                }
            }

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
        height: 500px;
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
        margin-bottom: 30px;
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

    .return-login {
        width: 300px;
        margin-bottom: 20px;
    }

    .return-login a {
        font-size: 20px;
        color: #3498db;
        text-decoration: none;
    }

    .result-text {
        margin-left: 10px;
        font-size: 20px;
        color: black;
    }

    span {
        color: red;
        font-size: 5px;
        margin-left: 10px;
    }
</style>