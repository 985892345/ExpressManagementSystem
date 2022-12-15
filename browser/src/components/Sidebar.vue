<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      :default-active="onRoutes"
      :collapse="sidebarStore.collapse"
      background-color="#324157"
      text-color="#bfcbd9"
      active-text-color="#20a0ff"
      unique-opened
      router
    >
      <template v-if="accountStore.user.admin">
        <el-menu-item v-for='item in adminItems' :index="item.path" :key="item.path">
          <el-icon>
            <component :is="item.icon"></component>
          </el-icon>
          <template #title>{{ item.title }}</template>
        </el-menu-item>
      </template>
      <template v-else>
        <el-menu-item v-for='item in userItems' :index="item.path" :key="item.path">
          <el-icon>
            <component :is="item.icon"></component>
          </el-icon>
          <template #title>{{ item.title }}</template>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script setup>
import {computed, ref} from 'vue'
import {useSidebarStore} from '@/store/sidebar'
import {useRoute} from 'vue-router'
import {useAccountStore} from "@/store/account"

const userItems = [
  {
    icon: 'Box',
    path: '/express',
    title: '快递信息',
  },
  {
    icon: 'OfficeBuilding',
    path: '/company',
    title: '快递公司',
  },
  {
    icon: 'Shop',
    path: '/product',
    title: '商品信息',
  }
]

const adminItems = [
  {
    icon: 'Box',
    path: '/express',
    title: '快递信息',
  },
  {
    icon: 'Postcard',
    path: '/user',
    title: '用户管理',
  },
  {
    icon: 'OfficeBuilding',
    path: '/company',
    title: '快递公司',
  },
  {
    icon: 'Shop',
    path: '/product',
    title: '商品信息',
  }
]

const route = useRoute()
const onRoutes = computed(() => {
  return route.path;
})

const sidebarStore = useSidebarStore()
const accountStore = useAccountStore()

</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}

.sidebar > ul {
  height: 100%;
}
</style>
