package com.example.express.interceptor;

import com.example.express.exception.TokenException;
import com.example.express.utils.TokenUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token 拦截器，负责拦截 token 过期的请求
 *
 * @author 985892345
 * 2022/12/12 23:26
 */
public class TokenInterceptor implements HandlerInterceptor {
  
  @Override
  public boolean preHandle(
    @NotNull HttpServletRequest request,
    @NotNull HttpServletResponse response,
    @NotNull Object handler
  ) {
    String token = request.getHeader("Authorization");
    String username = TokenUtil.getUsernameByToken(token);
    if (username == null) {
      // token 已过期
      throw new TokenException();
    }
    return true;
  }
}
