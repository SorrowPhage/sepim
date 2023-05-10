服务端口

- 后端服务器端口：8085
- 网站端口：8080
- 资源服务器端口：8088



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

- 查看所有文档：可以查看用户上传的文档

- 查看文档：可以查看文档内容以及评论

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

- 用户界面：展示聊天记录，用户自述以及用户的文档列表

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





