package com.example.express.exception;

/**
 * .
 *
 * @author 985892345
 * 2022/12/13 14:11
 */
public class LoginExpiredException extends GlobalException {
  public LoginExpiredException() {
    super(20001, "登录过期");
  }
}