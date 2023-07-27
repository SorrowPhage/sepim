服务端口

- 后端服务器端口：8085

- 网站端口：8080

- 资源服务器端口：8088

- Redis:
  ```yaml
  redis:
    #Redis服务器地址
    host: 192.168.102.100
    #端口号
    port: 6379
  ```
  
- RabbitMQ：

  ```yaml
  spring:
    application:
      name: springboot_rabbitmq
    rabbitmq:
      host: 192.168.102.100
      port: 5673
      username: ems
      password: ems
      virtual-host: /ems
  ```

  







新增功能：

- 登录

- **注册/找回密码：注册/找回密码功能需先开启邮箱POP3/SMTP服务**

  支持qq邮箱或163邮箱，这里使用的是qq邮箱

  ```yaml
    mail:
        #smtp服务主机  qq邮箱则为smtp.qq.com;163邮箱是smtp.163.com
      host: smtp.qq.com
  ```

- 搜索:默认搜索为文档，可以选择用户

  ![](https://s3.bmp.ovh/imgs/2023/05/10/ed050a33d86cd312.png)

- 查看所有文档：可以查看用户上传的文档

  ![](https://s3.bmp.ovh/imgs/2023/05/11/b0a82c42df893953.png)

- 查看文档：可以查看文档内容以及评论

  ![](https://s3.bmp.ovh/imgs/2023/05/10/2ca4d9370ee4f419.png)

- 新建文档：使用了mavon-editor组件，该组件支持Markdown文档的编辑，根据需要需要实现change，save等事件 

  ```vue
  <mavon-editor
  	v-model="md"
  	:placeholder="'Edit ···'"
  	ref="md"
  	@imgAdd="renderImgAdd"
  	@imgDel="renderImgDel"
  	@change="change"
  	@save="save"
      :toolbarsBackground="'#afb8c1'"
      :externalLink="externalLink"
      style="height: calc(100vh - 50px)"
  />
  ```

  ![](https://s3.bmp.ovh/imgs/2023/05/11/48b48b0babee919d.png)

- 用户界面：展示聊天记录，用户自述以及用户的文档列表

  ![](https://s3.bmp.ovh/imgs/2023/05/10/2a3ad99b48b5ba23.png)

- 个人信息：修改个人信息，头像上传使用了vueCropper组件

  ```vue
  <vueCropper
      ref="cropper"
      :autoCrop="option.autoCrop"
      :autoCropHeight="option.autoCropHeight"
      :autoCropWidth="option.autoCropWidth"
      :canMove="option.canMove"
      :canMoveBox="option.canMoveBox"
      :canScale="option.canScale"
      :centerBox="option.centerBox"
      :fixed="option.fixed"
      :fixedBox="option.fixedBox"
      :fixedNumber="option.fixedNumber"
      :full="option.full"
      :high="option.high"
      :img="option.img"
      :info="option.info"
      :mode="option.mode"
      :original="option.original"
      :outputSize="option.size"
      :outputType="option.outputType"
      @imgLoad="imgLoad"
      @realTime="realTime"
  ></vueCropper>
  ```

- 修改密码：修改用户密码

- 音乐

- 天气:使用和风天气的插件

  ```vue
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
  ```

  ![](https://s3.bmp.ovh/imgs/2023/05/11/36bbd3af39fad752.png)

- 私信：使用了WebSocket

  ![](https://s3.bmp.ovh/imgs/2023/05/10/6517e6fe576538b0.png)

- 文件上传：在新建文件时可以选择上传本地的md文件

# Bug
1.用户名更改时，该用户之前写的文章会使用之前的名字。应该使用多表查询解决。评论也是如此。<font color="Red">(已修复)</font>  <br>
2.新用户注册时，若不上传头像，在一些地方的使用中没有像导航也一样做处理。



