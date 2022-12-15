package com.example.express.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 规定：
 * 10000    数据正常
 *
 * 20000    token 异常，需要刷新
 * 20001    登录过期
 *
 * 30000    未知报错
 *
 * @author 985892345
 * 2022/12/12 14:45
 */
@Data
@AllArgsConstructor
public class ResponseBean<T> {
  private T data;
  private int code;
  private String info;
  
  public static <T> ResponseBean<T> success(T data) {
    return new ResponseBean<>(data, 10000, "success");
  }
  
  public static <T> ResponseBean<T> error(int code, String info) {
    return new ResponseBean<>(null, code, info);
  }
  
  public static <T> ResponseBean<T> errorOfToken() {
    return error(20000, "token 异常");
  }
  
  public static <T> ResponseBean<T> errorOfLoginExpired() {
    return error(20001, "登录过期");
  }
}
