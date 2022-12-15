import request from "@/utils/request";
import {getRefreshToken, getToken} from "@/utils/token";
import {useAccountStore} from "@/store/account";
import {stringify} from "qs";

// 登录
export const login = (username, password) => {
  return request({
    url: '/account/login',
    method: "post",
    data: stringify({
      username,
      password
    })
  })
}

// 刷新 token
export const refreshToken = () => {
  const accountStore = useAccountStore()
  return request({
    url: '/account/refreshToken',
    method: "post",
    data: stringify({
      refreshToken: getRefreshToken(),
      username: accountStore.user.username
    })
  })
}

// 登出
export const logout = () => {
  const accountStore = useAccountStore()
  return request({
    url: '/account/logout',
    method: "post",
    data: stringify({
      username: accountStore.user.username
    })
  })
}

// 注册
export const register = (username, password, phone) => {
  return request({
    url: '/account/register',
    method: "post",
    data: stringify({
      username: username,
      password: password,
      phone: phone
    })
  })
}

// 得到自身数据
export const getSelf = () => {
  return request({
    url: '/account/self',
    method: "get",
    headers: {
      'Authorization': "Bearer " + getToken()
    }
  })
}

// 得到所有用户信息
export const getUser = (current, size) => {
  return request({
    url: '/user/all',
    method: 'get',
    params: {
      current: current,
      size: size
    },
  })
}

// 改变用户数据
export const changeUser = (userId, address, phone, sex, isAdmin) => {
  return request({
    url: '/user/change',
    method: 'post',
    data: stringify({
      userId: userId,
      address: address,
      phone: phone,
      sex: sex,
      isAdmin: isAdmin
    }),
  })
}

