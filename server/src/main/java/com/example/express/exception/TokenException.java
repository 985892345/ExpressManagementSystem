package com.example.express.exception;

/**
 * .
 *
 * @author 985892345
 * 2022/12/13 14:11
 */
public class TokenException extends GlobalException {
  public TokenException() {
    super(20000, "token 异常");
  }
}
