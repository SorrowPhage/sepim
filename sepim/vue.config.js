const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {// 匹配所有以 '/api1'开头的请求路径
        target: 'http://localhost:8085/',// 代理目标的基础路径
        changeOrigin: true,
        pathRewrite: {'^/api': ''},
      },
    }
  }
})
