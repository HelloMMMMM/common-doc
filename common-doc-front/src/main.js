import Vue from 'vue'

// element-ui
import element from "./components/element";
Vue.use(element);

import App from './App.vue'

// 自定义全局css
import './css/index.css'

// 自定义网络相关
import api from './components/api'
import { post } from './components/http';
Vue.prototype.$api = api;
Vue.prototype.$post = post;

new Vue({
    render: h => h(App),
}).$mount('#app')