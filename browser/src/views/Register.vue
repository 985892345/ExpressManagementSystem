<template>
  <div class="register-parent">
    <div class="register-wrap">
      <div class="register-title">快递管理系统</div>
      <el-form class="register-content" :model="param" :rules="rules" ref="loginForm">
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
        <el-form-item prop="password2">
          <el-input
            type="password"
            placeholder="确认密码"
            v-model="param.password2"
            @keyup.enter="submitForm()"
          >
            <template #prepend>
              <el-button :icon="Lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input
            type="phone"
            placeholder="电话"
            v-model="param.phone"
            @keyup.enter="submitForm()"
          >
            <template #prepend>
              <el-button :icon="Iphone"></el-button>
            </template>
            <template #append>
              <el-button type="primary" @click="sendCode" :icon="Promotion"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-input
            type="number"
            placeholder="验证码"
            v-model="param.code"
            @keyup.enter="submitForm()"
          >
            <template #prepend>
              <el-button :icon="Message"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="register-btn">
          <el-button type="primary" @click="submitForm">注册</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import {Iphone, Lock, Message, Promotion, User} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";
import {reactive, ref} from "vue";
import {register} from "@/api";
import {ElMessage} from "element-plus";

const code = ref()

const router = useRouter();
const param = reactive({
  username: '',
  password: '',
  password2: '',
  phone: '',
  code: '',
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
const checkPassword2 = (rule, value, callback) => {
  if (value.length < 5) {
    callback(new Error('密码不能少于 5 位！'))
  } else {
    if (value !== param.password) {
      callback(new Error('确认密码与密码不一致'))
    } else {
      callback()
    }
  }
}
const checkPhone = (rule, value, callback) => {
  if (value.length !== 11) {
    callback(new Error('电话号码需要 11 位'))
  } else {
    callback()
  }
}
const checkCode = (rule, value, callback) => {
  if (value !== code.value) {
    callback(new Error('验证码错误'))
  } else {
    callback()
  }
}
const rules = {
  username: [{required: true, trigger: 'blur', validator: checkUsername}],
  password: [{required: true, trigger: 'blur', validator: checkPassword}],
  password2: [{required: true, trigger: 'blur', validator: checkPassword2}],
  phone: [{required: true, trigger: 'blur', validator: checkPhone}],
  code: [{required: true, trigger: 'blur', validator: checkCode}],
};
const loginForm = ref()

const submitForm = () => {
  loginForm.value.validate(() => {
    register(param.username, param.password, param.phone).then(res => {
      if (res.data.code === 10000) {
        ElMessage.success("注册成功")
        router.push('/login')
      } else {
        ElMessage.error('注册失败：' + res.data.info)
      }
    }).catch(err => {
      ElMessage.error('注册失败：' + err.message)
    })
  });
}

const sendCode = () => {
  if (param.phone.length !== 11) {
    ElMessage.error("电话号码错误！")
  } else {
    ElMessage.success("已发送验证码")
    code.value = "1234"
  }
}
</script>

<style scoped>
.register-parent {
  display: flex;
  background: rgba(43, 151, 255, 0.2);
  width: 100%;
  height: 100%;
  justify-content:center;
  align-items:center;
}

.register-wrap {
  margin: 0 0 100px;
  border-radius: 5px;
  background: white;
}

.register-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  border-bottom: 1px solid #ddd;
}

.register-content {
  padding: 30px 30px;
}

.register-btn {
  text-align: center;
}

.register-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

</style>
