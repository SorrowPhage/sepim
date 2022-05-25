import Vue from 'vue'
import App from './App.vue'

import store from './store'
// 引入路由器
import VueRouter from 'vue-router'
import router from './router/index'
import VueCropper from "vue-cropper";
import {MessageBox,Message,Pagination} from "element-ui";
Vue.prototype.$confirm = MessageBox.confirm
Vue.prototype.$message=Message


// 按需引入element-ui组件
import { RadioGroup, RadioButton, Menu, 
  Submenu, MenuItemGroup, MenuItem, Avatar, 
  PageHeader, Descriptions, DescriptionsItem, 
  Breadcrumb,BreadcrumbItem,DatePicker,Steps
  ,Step,Form,FormItem,Input,Dialog,Button,Carousel,CarouselItem,
  Calendar,Table,TableColumn,Radio} from 'element-ui'

Vue.config.productionTip = false
// 使用插件
Vue.use(VueRouter)
Vue.use(VueCropper);
// 注册全局组件
Vue.component(RadioGroup.name, RadioGroup)
Vue.component(RadioButton.name, RadioButton)
Vue.component(Menu.name, Menu)
Vue.component(Submenu.name, Submenu)
Vue.component(MenuItemGroup.name, MenuItemGroup)
Vue.component(MenuItem.name, MenuItem)
Vue.component(Avatar.name, Avatar)
Vue.component(PageHeader.name, PageHeader)
Vue.component(Descriptions.name, Descriptions)
Vue.component(DescriptionsItem.name, DescriptionsItem)
Vue.component(Breadcrumb.name, Breadcrumb)
Vue.component(BreadcrumbItem.name, BreadcrumbItem)
Vue.component(DatePicker.name, DatePicker)
Vue.component(Steps.name, Steps)
Vue.component(Step.name, Step)
Vue.component(Form.name, Form)
Vue.component(FormItem.name, FormItem)
Vue.component(Input.name, Input)
Vue.component(Dialog.name, Dialog)
Vue.component(Button.name, Button)
Vue.component(Carousel.name, Carousel)
Vue.component(CarouselItem.name, CarouselItem)
Vue.component(Calendar.name, Calendar)
Vue.component(Table.name, Table)
Vue.component(TableColumn.name, TableColumn)
Vue.component(Radio.name, Radio)
Vue.component(Pagination.name, Pagination)



new Vue({
  el:'#app',
  render: h => h(App),
  store:store,
  router,
})
