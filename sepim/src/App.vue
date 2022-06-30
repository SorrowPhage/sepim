<template>
    <div>
        <router-view v-if="showState"></router-view>
    </div>
</template>

<script>
export default {
    name: 'App',
    data(){
        return{
            showState: true,
        }
    },
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

