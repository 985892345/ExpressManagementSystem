package com.example.express.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.express.bean.LoginBean;
import com.example.express.bean.ResponseBean;
import com.example.express.bean.TokenBean;
import com.example.express.entity.user.UserEntity;
import com.example.express.entity.user.UserSecretEntity;
import com.example.express.entity.user.UserTokenEntity;
import com.example.express.mapper.user.UserMapper;
import com.example.express.mapper.user.UserSecretMapper;
import com.example.express.mapper.user.UserTokenMapper;
import com.example.express.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 账户信息模块
 *
 * 主要负责：
 * 1、注册
 * 2、登录
 * 3、登出
 * 4、鉴权
 * 5、得到自身信息
 *
 * 其中鉴权采用互联网流行的 OAuth2.0 方案，使用 token 和 refreshToken 进行用户身份判断
 *
 * @author 985892345
 * 2022/12/12 20:03
 */
@RestController
@RequestMapping(value = "/account")
public class AccountController {
  
  private final UserMapper userMapper;
  
  private final UserSecretMapper userSecretMapper;
  
  private final UserTokenMapper userTokenMapper;
  
  public AccountController(UserMapper userMapper, UserSecretMapper userSecretMapper, UserTokenMapper userTokenMapper) {
    this.userMapper = userMapper;
    this.userSecretMapper = userSecretMapper;
    this.userTokenMapper = userTokenMapper;
  }
  
  /**
   * ResponseBean 约定：
   * <p>
   * code: 10010
   * info: 账号或者密码错误
   */
  @PostMapping("/login")
  public ResponseBean<LoginBean> login(
    @RequestParam String username,
    @RequestParam String password
  ) {
    // 采用 SHA 算法加密密码，服务器上不保存明文密码
    String pw = sha(password);
    UserSecretEntity secret = userSecretMapper.selectById(username);
    if (secret == null || !secret.getPassword().equals(pw)) {
      return ResponseBean.error(10010, "账号或者密码错误");
    } else {
      String token = TokenUtil.generateToken(username);
      String refreshToken = TokenUtil.generateRefreshToken(username);
      userTokenMapper.deleteById(username);
      // 插入 refreshToken 到数据库
      userTokenMapper.insert(new UserTokenEntity(username, refreshToken));
      // 根据 username 查询 UserEntity
      QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("username", username);
      UserEntity user = userMapper.selectOne(queryWrapper);
      return ResponseBean.success(new LoginBean(user, new TokenBean(token, refreshToken)));
    }
  }
  
  /**
   * ResponseBean 约定：
   * <p>
   * code: 10010
   * info: 该用户名已被注册
   */
  @PostMapping("/register")
  public ResponseBean<String> register(
    @RequestParam String username,
    @RequestParam String password,
    @RequestParam long phone
  ) {
    boolean isExist = userSecretMapper.selectById(username) != null;
    if (isExist) {
      return ResponseBean.error(10010, "该用户名已被注册");
    } else {
      userMapper.insert(new UserEntity(0, username, null, phone, null, false));
      // 采用 SHA 算法加密密码，服务器上不保存明文密码
      String pw = sha(password);
      userSecretMapper.insert(new UserSecretEntity(username, pw));
      return ResponseBean.success("注册成功");
    }
  }
  
  @PostMapping("/logout")
  public ResponseBean<String> logout(
    @RequestParam String username
  ) {
    userTokenMapper.deleteById(username);
    return ResponseBean.success("注销成功");
  }
  
  @PostMapping("/refreshToken")
  public ResponseBean<TokenBean> refreshToken(
    @RequestParam String refreshToken,
    @RequestParam String username
  ) {
    // 先检测 refreshToken 是否有效
    boolean isValid = username.equals(TokenUtil.getUsernameByRefreshToken(refreshToken));
    if (!isValid) {
      return ResponseBean.errorOfLoginExpired();
    } else {
      String token = TokenUtil.generateToken(username);
      String newRefreshToken = TokenUtil.generateRefreshToken(username);
      // 插入新的 refreshToken 到数据库
      userTokenMapper.updateById(new UserTokenEntity(username, newRefreshToken));
      return ResponseBean.success(new TokenBean(token, newRefreshToken));
    }
  }
  
  @GetMapping("/self")
  public ResponseBean<UserEntity> getSelf(
    @RequestHeader(value = "Authorization") String token
  ) {
    String username = TokenUtil.getUsernameByToken(token);
    if (username == null) {
      return ResponseBean.errorOfToken();
    }
    // 根据 username 查询 UserEntity
    QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", username);
    UserEntity user = userMapper.selectOne(queryWrapper);
    return ResponseBean.success(user);
  }
  
  private static String sha(String inStr) {
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-256");
      byte[] byteArray = inStr.getBytes(StandardCharsets.UTF_8);
      byte[] md5Bytes = sha.digest(byteArray);
      StringBuilder hexValue = new StringBuilder();
      for (byte md5Byte : md5Bytes) {
        int val = ((int) md5Byte) & 0xff;
        if (val < 16) {
          hexValue.append("0");
        }
        hexValue.append(Integer.toHexString(val));
      }
      return hexValue.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
