<template>
    <div>
        <div id="he-plugin-simple"></div>
    </div>
</template>

<script>
export default {
    name: "HeFenWeather",
    created() {
        window.WIDGET = {
            "CONFIG": {
                "modules": "01234",
                "background": "4",
                "backgroundColor": "FFFFFF",
                "tmpColor": "40A0FF",
                "tmpSize": "16",
                "cityColor": "40A0FF",
                "citySize": "16",
                "aqiColor": "40A0FF",
                "aqiSize": "16",
                "weatherIconSize": "24",
                "alertIconSize": "18",
                "padding": "10px 10px 10px 10px",
                "shadow": "0",
                "language": "auto",
                "fixed": "false",
                "vertical": "top",
                "horizontal": "left",
                "key": "b9d32d2e8b89458585ac6be2391bb59a"
            }
        };
        let script = document.createElement('script');
        script.type = "text/javascript"
        script.src="https://widget.qweather.net/simple/static/js/he-simple-common.js?v=2.0"
        document.getElementsByTagName('head')[0].appendChild(script);
    }
}
</script>

<style scoped>


</style>