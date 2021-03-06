import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import Axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css'

import store from './store/index'
import BaiduMap from 'vue-baidu-map'
import moment from 'moment'
import qs from 'qs'
// 注册全局钩子用来拦截导航
router.beforeEach((to, from, next) => {
  const token = store.state.token
  if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
    if (token) { // 通过vuex state获取当前的token是否存在
      next()
    } else {
      console.log('该页面需要登陆')
      next({
        path: '/login'
        // query: {redirect: to.fullPath} // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
    }
  } else {
    next()
  }
})

// 设置反向代理，前端请求默认发送到 http://localhost:8080/api
Axios.defaults.baseURL = 'http://345ea19162.wicp.vip:31216/Manage-0.0.1-SNAPSHOT/'
// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = Axios
Vue.prototype.$qs = qs;
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(BaiduMap, {
  ak: 'lbMpaUfPfjSzxIzYfnW0NGfyeW1yTfxS'
})
/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router,
  moment,
  // 注意这里
  store,
  components: { App },
  template: '<App/>'
})
