import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)


import "vue-tlpick/src/lib/createjs"
import "vue-tlpick/src/lib/TweenMax"
import "vue-tlpick/lib/vue-tlpick.css"
import tlpick from "vue-tlpick"
Vue.use(tlpick);


export default new Router({
  base: '/vuemap/',
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
    }
  ]
})
