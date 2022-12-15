<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <div class="collapse-btn" @click="collapseChange">
      <el-icon v-if="sidebarStore.collapse">
        <Expand/>
      </el-icon>
      <el-icon v-else>
        <Fold/>
      </el-icon>
    </div>
    <div class="logo">快递管理系统</div>
    <div class="header-right">
      <div class="header-user-con">
        <!-- 用户头像 -->
        <el-avatar class="user-avatar" :size="30" :icon="Avatar"/>
        <!-- 用户名下拉菜单 -->
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
					<span class="el-dropdown-link">
						{{ accountStore.user.username }}
						<el-icon class="el-icon--right">
							<arrow-down/>
						</el-icon>
					</span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>
<script setup>
import {useSidebarStore} from '@/store/sidebar'
import {useRouter} from 'vue-router'
import {useAccountStore} from "@/store/account"
import {Avatar} from "@element-plus/icons-vue"

const accountStore = useAccountStore()

const sidebarStore = useSidebarStore()
// 侧边栏折叠
const collapseChange = () => {
  sidebarStore.handleCollapse()
};

// 用户名下拉菜单选择事件
const router = useRouter()
const handleCommand = (command) => {
  if (command === 'logout') {
    accountStore.reset()
    router.push('/login')
  }
};
</script>
<style scoped>
.header {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: white;
}

.collapse-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  float: left;
  padding: 0 21px;
  cursor: pointer;
}

.header .logo {
  float: left;
  width: 250px;
  line-height: 70px;
}

.header-right {
  float: right;
  padding-right: 50px;
}

.header-user-con {
  display: flex;
  height: 70px;
  align-items: center;
}

.user-name {
  margin-left: 10px;
}

.user-avatar {
  margin-left: 20px;
}

.el-dropdown-link {
  color: #fff;
  cursor: pointer;
  display: flex;
  align-items: center;
}
</style>
