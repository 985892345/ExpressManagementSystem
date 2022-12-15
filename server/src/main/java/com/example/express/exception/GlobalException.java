package com.example.express.exception;

/**
 * .
 *
 * @author 985892345
 * 2022/12/13 0:08
 */
public class GlobalException extends RuntimeException {
  
  public int code;
  
  public GlobalException(int code, String message) {
    super(message);
    this.code = code;
  }
  
  public GlobalException(int code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }
}