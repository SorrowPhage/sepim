<template>
    <div class="main">
        xx
        <canvas class="canvas"></canvas>
    </div>
</template>

<script>
export default {
    name: "HappyBirthday",
    data() {
        return{
        
        }
    },
}
</script>

<style scoped>
* {
    box-sizing: border-box;
}

.main {
    margin-top: 50px;
    /*background-color: rgb(228, 228, 228);*/
    height: 100%;
    /*overflow: auto;*/
}

a {
    color: #ebf7f4;
}
.body--ready {
    background: -webkit-linear-gradient(top, rgb(203, 235, 219) 0%, rgb(55, 148, 192) 120%);
    background: -moz-linear-gradient(top, rgb(203, 235, 219) 0%, rgb(55, 148, 192) 120%);
    background: -o-linear-gradient(top, rgb(203, 235, 219) 0%, rgb(55, 148, 192) 120%);
    background: -ms-linear-gradient(top, rgb(203, 235, 219) 0%, rgb(55, 148, 192) 120%);
    background: linear-gradient(top, rgb(203, 235, 219) 0%, rgb(55, 148, 192) 120%);
}
.text {
    position: fixed;
    bottom: 100px;
    text-align: center;
    width: 100%;
}
.canvas {
    margin: 0 auto;
    display: block;
}
img#logo {
    width: 128px;
    background-size: cover;
    border-radius: 200px;
    box-shadow: 0px 0px 40px rgba(63, 81, 181, 0.72);
    border: 3px solid #00a0ff;
    opacity: 1;
    margin: 0 auto;
    margin-top: 20px;
    margin-bottom: 20px;
    transition: all 1.0s;
}
#logo:hover {
    box-shadow: 0 0 10px #fff;
    -webkit-box-shadow: 0 0 19px #fff;
    transform: rotate(360deg);
    -ms-transform: rotate(360deg); /* IE 9 */
    -moz-transform: rotate(360deg); /* Firefox */
    -webkit-transform: rotate(360deg); /* Safari 和 Chrome */
    -o-transform: rotate(360deg); /* Opera */
    filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=3);
}
.cs {
    width: 100%;
    height: 100%;
    margin: 0 auto;
    position: absolute;
    text-align: center;
}
.text {
    position: fixed;
    bottom: 80px;
    text-align: center;
    width: 100%;
    font-weight: bold;
}
.text-right {
    position: fixed;
    bottom: 50px;
    text-align: right;
    width: 100%;
    font-weight: bold;
}
</style>