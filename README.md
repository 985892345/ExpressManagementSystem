# ExpressManagementSystem
 某个要求两周就学会前后端开发的高校的实训作业：快递管理系统。采取 SpringBoot + Vue + MySql 开发

推荐“一天”就能学会的 SprintBoot + Vue 的 B 站视频：[【1天搞定SpringBoot+Vue全栈开发】](https://www.bilibili.com/video/BV1nV4y1s7ZN/?share_source=copy_web&vd_source=590b9e53dd8d9fbbcdc1465639d06cb2)

# **注意：本项目仅供学习交流，严禁用于商业以及课程作业中。请不要询问作者问题，作者不会进行解答**

## 使用框架

### 后端

#### SpringBoot

使用 SpringBoot2.7.6，最新的 SpringBoot3刚发布，有很多坑

#### MyBitsPlus

整合 MyBits 功能，特别适合快速开发

#### Lombok

用来简化 java 数据类 getter 和 setter 写法臃肿问题

### 前端

#### Vue

使用最新的 Vue3.2 版本

考虑到时间问题，所以没有使用 VSCode，直接用的 WebStorm 创建的项目

#### axios

网络请求

#### element-plus

组件库

#### mockjs

模拟后端请求

## 后端

### 接口

#### AccountController

用于处理登录、注册等相关接口

考虑到登录安全问题（其实是我强迫症），加上了 OAuth2.0 的 Token 机制，使用了 token 与 refreshToken。



