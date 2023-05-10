const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  publicPath: './',
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {// 匹配所有以 '/api1'开头的请求路径
        target: 'http://localhost:8085/',// 代理目标的基础路径
        changeOrigin: true,
        pathRewrite: {'^/api': ''},
      },
      '/socket': {
        //webSocket代理
        target: 'ws://localhost:8080/websocket', // 内网
        /*target: 'ws://你的服务器地址/parkingServer', // 外网*/
        /*target: 'ws://你的服务器地址/parkingServer',//本地测试*/
        ws:true,//开启ws, 如果是http代理此处可以不用设置
        changeOrigin: true,
        pathRewrite: {
          '^/socket': ''
        }
      }

    }
  },

})
