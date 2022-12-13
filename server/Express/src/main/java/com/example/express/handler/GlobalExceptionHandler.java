package com.example.express.handler;

import com.example.express.bean.ResponseBean;
import com.example.express.exception.GlobalException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author 985892345
 * 2022/12/13 0:13
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseBean<String> handle(Exception e) {
    if (e instanceof GlobalException exception) {
      return ResponseBean.error(exception.code, exception.getMessage());
    } else {
      e.printStackTrace();
      return ResponseBean.error(30000, e.getMessage());
    }
  }
}
