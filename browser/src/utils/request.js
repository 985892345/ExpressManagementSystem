import axios from "axios";
import {BASE_URL} from "@/config";
import {ElMessage} from "element-plus";
import {refreshToken} from "@/api";
import {getToken, setToken} from "@/utils/token";
import {useRouter} from "vue-router";

const request = axios.create({
  baseURL: BASE_URL,
})

// 是否正在刷新的标记
let isRefreshing = false
// 重试队列，每一项将是一个待执行的函数形式
let requests = []

request.interceptors.request.use(
  config => {
    if (isRefreshing) {
      return new Response(res => {
        requests.push(() => {
          res(request(config))
        })
      })
    } else {
      console.log("url = " + config.url);
      if (!config.url.startsWith("/account")) {
        config.headers['Authorization'] = 'Bearer ' + getToken()
      }
    }
    return config
  },
  error => {
    console.error(error)
    ElMessage.error(error.message)
    return Promise.reject(error)
  }
)

request.interceptors.response.use(
  response => {
    const router = useRouter()
    const responseData = response.data
    if (responseData.code === 20000) {
      if (!isRefreshing) {
        isRefreshing = true
        // 尝试刷新 refreshToken
        return refreshToken().then(res => {
          if (res.data.code === 10000) {
            setToken(res.data.auth.token)
            requests.forEach(cb => cb())
            requests = []
            return request(response.config)
          } else {
            router.push('/login')
          }
        }, err => {
          router.push('/login')
        }).finally(() => {
          isRefreshing = false
        })
      }
    } else if (responseData.code === 20001) {
      // refreshToken 失效，直接跳转登录界面
      router.push('/login')
      ElMessage.warning('登录过期')
      return Promise.reject(new Error("登录过期"))
    }
    return response
  },
  error => {
    console.error(error)
    ElMessage.error(error.message)
    return Promise.reject(error)
  }
)

export default request
