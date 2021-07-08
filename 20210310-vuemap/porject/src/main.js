// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import jQuery from 'jquery'
import { VueJsonp } from 'vue-jsonp'
Vue.use(VueJsonp);

import {BaiduMap} from "vue-baidu-map";
import logotitle from './components/title';
import dataManage from './components/dataManage';
import rangeSelector from './components/rangeSelector';
import timeLine from './components/timeLine';
import echartsPie from './components/echartsPie'
import './assets/css/style.css';
import './assets/jqwidgets-scripts/jqwidgets/styles/jqx.base.css';

// import echarts from 'echarts'
// Vue.prototype.$echarts = echarts;

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {
    App,
    BaiduMap,
    logotitle,
    dataManage,
    rangeSelector,
    timeLine,
    echartsPie,
  },
  template: '<App/>'
});
