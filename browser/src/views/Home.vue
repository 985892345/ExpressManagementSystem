<template>
  <div class="wrapper">
    <Header></Header>
    <Sidebar></Sidebar>
    <div class="content-box" :class="{ 'content-collapse': sidebarStore.collapse }">
      <Tags></Tags>
      <div class="content">
        <router-view v-slot="{ Component }">
          <transition name="move" mode="out-in">
            <keep-alive :include="tagsStore.nameList">
              <component :is="Component"></component>
            </keep-alive>
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import Header from "@/components/Header.vue"
import Sidebar from "@/components/Sidebar.vue"
import Tags from "@/components/Tags.vue"
import {useSidebarStore} from "@/store/sidebar"
import {useTagsStore} from "@/store/tags"
import {useAccountStore} from "@/store/account";
import {getSelf} from "@/api";
import router from "@/router";
import {ElMessage} from "element-plus";
const sidebarStore = useSidebarStore()
const tagsStore = useTagsStore()
const accountStore = useAccountStore()

getSelf().then(res => {
  if (res.data.code === 10000) {
    accountStore.user = res.data.data
  } else {
    accountStore.reset()
    ElMessage.error("登录过期")
    router.push("/login")
  }
}).catch(err => {
  console.log("getSelf   then")
  accountStore.reset()
  ElMessage.error("登录过期")
  router.push("/login")
})
</script>

<style scoped>

</style>
