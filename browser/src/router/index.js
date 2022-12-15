import {createRouter, createWebHashHistory} from "vue-router";
import {getToken} from "@/utils/token";
import Home from "@/views/Home.vue";

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      redirect: '/express'
    },
    {
      path: '/login',
      component: () => import('../views/Login.vue')
    },
    {
      path: '/',
      name: "Home",
      component: Home,
      children: [
        {
          path: '/express',
          name: 'express',
          meta: {
            title: '快递信息',
          },
          component: () => import('../views/Express.vue')
        },
        {
          path: '/user',
          name: 'user',
          meta: {
            title: '用户管理',
          },
          component: () => import('../views/User.vue')
        },
        {
          path: '/company',
          name: 'company',
          meta: {
            title: '公司信息',
          },
          component: () => import('../views/Company.vue')
        },
        {
          path: '/product',
          name: 'product',
          meta: {
            title: '商品信息',
          },
          component: () => import('../views/Article.vue')
        }
      ]
    },
    {
      path: '/register',
      component: () => import('../views/Register.vue')
    }
  ]
})

const whiteList = ['/login', '/register']

router.beforeEach((to, from, next) => {
  const token = getToken()
  if (token !== 'null') {
    if (to.path === '/login') {
      next('/')
    } else {
      next()
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      console.log("redirect")
      next(`/login?redirect=${to.path}`)
    }
  }
})

export default router
