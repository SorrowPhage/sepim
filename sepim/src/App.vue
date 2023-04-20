<template>
    <div>
        <router-view></router-view>
    </div>
</template>

<script>
export default {
    name: 'App',
    provide() {
        return {
            reload: this.reload
        }
    },
    methods:{
        reload(){
            this.showState = false;
            this.$nextTick(()=>{
                this.showState = true;
            })
        }
    },
    components: {}
}
</script>

