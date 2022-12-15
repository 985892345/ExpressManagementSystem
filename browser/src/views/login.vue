<template>
  <div class="login-parent">
    <div class="login-wrap">
      <div class="login-title">快递管理系统</div>
      <el-form class="login-content" :model="param" :rules="rules" ref="loginForm">
        <el-form-item prop="username">
          <el-input v-model="param.username" placeholder="用户名">
            <template #prepend>
              <el-button :icon="User"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="密码"
            v-model="param.password"
            @keyup.enter="submitForm()"
          >
            <template #prepend>
              <el-button :icon="Lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div>
          <button class="login-register" @click="register">注册账号</button>
          <button class="login-forget" @click="forget">忘记密码</button>
        </div>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive, ref} from "vue";
import {useAccountStore} from "@/store/account";
import {ElMessage} from "element-plus";
import {Lock, User} from "@element-plus/icons-vue";
import {setToken} from "@/utils/token";
import {login} from "@/api";

const router = useRouter();
const param = reactive({
  username: '',
  password: ''
});
const checkUsername = (rule, value, callback) => {
  if (value.length < 5) {
    callback(new Error('用户名不能少于 5 位！'))
  } else {
    callback()
  }
}
const checkPassword = (rule, value, callback) => {
  if (value.length < 5) {
    callback(new Error('密码不能少于 5 位！'))
  } else {
    callback()
  }
}
const rules = {
  username: [{required: true, trigger: 'blur', validator: checkUsername}],
  password: [{required: true, trigger: 'blur', validator: checkPassword}]
};
const loginForm = ref()

const submitForm = () => {
  loginForm.value.validate(() => {
    const accountStore = useAccountStore()
    login(param.username, param.password).then(res => {
      if (res.data.code === 10000) {
        ElMessage.success('登录成功');
        setToken(res.data.data.auth.token)
        accountStore.setUser(res.data.data.user)
        const redirect = router.currentRoute.value.query.redirect
        if (typeof redirect === "string") {
          router.push(redirect)
        } else {
          router.push('/')
        }
      } else {
        ElMessage.error('登录失败, ' + res.data.info);
      }
    }).catch(err => {
      console.log(err)
      ElMessage.error('登录失败：' + err.message)
    });
    // setToken("123")
    // const redirect = router.currentRoute.value.query.redirect
    // if (typeof redirect === "string") {
    //   router.push(redirect)
    // } else {
    //   router.push('/')
    // }
  });
}

const register = () => {
  router.push('/register')
}

const forget = () => {
  ElMessage.error("暂未实现")
}

</script>

<style scoped>
.login-parent {
  display: flex;
  background: rgba(43, 151, 255, 0.2);
  width: 100%;
  height: 100%;
  justify-content:center;
  align-items:center;
}

.login-wrap {
  margin: 0 0 100px;
  border-radius: 5px;
  background: white;
}

.login-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  border-bottom: 1px solid #ddd;
}

.login-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-register, .login-forget {
  border: none;
  background: transparent;
  color: #397bef;
  margin-bottom: 10px;
  cursor: pointer;
}

.login-forget {
  float: right;
}

</style>
