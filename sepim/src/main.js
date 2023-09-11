import Vue from 'vue'
import App from './App.vue'

import store from './store'
// 引入路由器
import VueRouter from 'vue-router'
import router from './router/index'
import VueCropper from "vue-cropper";
import {MessageBox, Message} from "element-ui";
import jquery from 'jquery'
Vue.prototype.$confirm = MessageBox.confirm
Vue.prototype.$message=Message
Vue.prototype.$ = jquery;


// import BootstrapVue from 'bootstrap-vue'
//
// Vue.use(BootstrapVue)
//
// import 'bootstrap/dist/css/bootstrap.css'
// import 'bootstrap-vue/dist/bootstrap-vue.css'

import  mavonEditor  from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

// 按需引入element-ui组件
import {
  RadioGroup,
  RadioButton,
  Menu,
  Submenu,
  MenuItemGroup,
  MenuItem,
  Avatar,
  PageHeader,
  Descriptions,
  DescriptionsItem,
  Breadcrumb,
  BreadcrumbItem,
  DatePicker,
  Steps
  ,
  Step,
  Form,
  FormItem,
  Input,
  Dialog,
  Button,
  Carousel,
  CarouselItem,
  Calendar,
  Table,
  TableColumn,
  Radio,
  Badge,
  Option,
  Select,
  Popover
  ,
  Empty,
  Aside,
  Tabs,
  TabPane,
  Link,
  Slider,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  Image,
  Row,
  Col,
  Timeline,
  TimelineItem,
  Container,
  Pagination,
  Main,
  Upload,
  Drawer,
  Divider,
  Tooltip
} from 'element-ui';

import { Loading } from 'element-ui';
Vue.use(Loading.directive);

//粒子特效
import VueParticles from 'vue-particles'
Vue.use(VueParticles)

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
Vue.component(Badge.name, Badge)
Vue.component(Option.name, Option)
Vue.component(Select.name, Select)
Vue.component(Popover.name, Popover)
Vue.component(Empty.name, Empty)
Vue.component(Aside.name, Aside)
Vue.component(Tabs.name, Tabs)
Vue.component(TabPane.name, TabPane)
Vue.component(Link.name, Link)
Vue.component(Slider.name, Slider)
Vue.component(Dropdown.name, Dropdown)
Vue.component(DropdownItem.name, DropdownItem)
Vue.component(DropdownMenu.name, DropdownMenu)
Vue.component(Image.name, Image)
Vue.component(Row.name, Row)
Vue.component(Col.name, Col)
Vue.component(Timeline.name, Timeline)
Vue.component(TimelineItem.name, TimelineItem)
Vue.component(Container.name, Container)
Vue.component(Aside.name, Aside)
Vue.component(Main.name, Main)
Vue.component(Upload.name, Upload)
Vue.component(Drawer.name, Drawer)
Vue.component(Divider.name, Divider)
Vue.component(Tooltip.name, Tooltip)


//树型组件
import Vue2OrgTree from 'vue2-org-tree'

Vue.use(Vue2OrgTree)

//拖拽·缩放·画布插件
import VueDraggableResizable from 'vue-draggable-resizable'
import 'vue-draggable-resizable/dist/VueDraggableResizable.css'
Vue.component('vue-draggable-resizable', VueDraggableResizable)





new Vue({
  el:'#app',
  render: h => h(App),
  store:store,
  router,
})
