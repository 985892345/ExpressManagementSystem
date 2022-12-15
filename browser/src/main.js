import { createApp } from 'vue'
import App from './App.vue'
import axios from "axios";
import {BASE_URL} from "@/config";
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import router from "@/router";
import {createPinia} from "pinia";
import 'element-plus/dist/index.css'
import './assets/css/icon.css'

axios.defaults.baseURL = BASE_URL

const app = createApp(App)

app.use(createPinia())

app.use(ElementPlus, {
  local: zhCn
})
// 注册 element-plus 图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(router)

app.mount('#app')
