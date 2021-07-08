import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
// eslint-disable-next-line no-unused-vars
import Score from '@/components/Score'
import Home from '@/components/common/Home'
import SelectInformation from '@/components/information/SelectInformation'
import InsertInformation from '@/components/information/InsertInformation'
import UpdateInformation from '@/components/information/UpdateInformation'
import mainPage from '@/components/information/mainPage'
import SelectScore from '@/components/score/SelectScore'
import InsertScore from '@/components/score/InsertScore'
import UpdateScore from '@/components/score/UpdateScore'
// eslint-disable-next-line no-unused-vars
import cookies from 'js-cookie'
// eslint-disable-next-line no-unused-vars
import Vuex from 'vuex'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: '/vue/',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
      redirect: '/login',
      children: [

        {
          path: '/information/select',
          name: 'SelectInformation',
          component: SelectInformation,
          meta: {
            requireAuth: true
          }

        },
        {
          path: '/information/update',
          name: 'UpdateInformation',
          component: UpdateInformation,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/information/insert',
          name: 'InsertInformation',
          component: InsertInformation,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/information/mainPage',
          name: 'mainPage',
          component: mainPage,
          meta: {
            requireAuth: true
          }
        },

        {
          path: '/score/select',
          name: 'SelectScore',
          component: SelectScore,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/score/update',
          name: 'UpdateScore',
          component: UpdateScore,
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/score/insert',
          name: 'InsertScore',
          component: InsertScore,
          meta: {
            requireAuth: true
          }
        }

      ]
    },

    {
      path: '/login',
      name: 'Login',
      component: Login
    }

  ]

})
