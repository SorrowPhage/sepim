<template>
    <div>
        <div class="left-entry">
            <div class="sp-nav">首页</div>
        </div>
    </div>
</template>

<script>
export default {
    name: "LeftNavi",
}
</script>

<style scoped>
.sp-nav {
    display: inline;
    margin-left: 10px;
    font-weight: bold;
    font-family: ui-monospace, SFMono-Regular, SF Mono, Menlo, Consolas, Liberation Mono, monospace !important;
    /*color: #5a8da1;*/
    color: #f1f2f3;
    font-size: 16px !important;
}
.sp-nav:hover {
    cursor: pointer;
    /*color: #409EFF;*/
}
.left-entry{
    display: flex;
    align-items: center;
    flex-shrink: 0;
    margin-right: 30px;
}
</style>