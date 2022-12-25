package com.example.express.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 15:21
 */
public class TokenUtil {
  
  /**
   * token 过期时间
   */
  private static final long TOKEN_EXPIRE_TIME = TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES);
  
  /**
   * refresh_token 过期时间
   */
  private static final long REFRESH_TOKEN_EXPIRE_TIME = TimeUnit.MILLISECONDS.convert(10, TimeUnit.DAYS);
  
  /**
   * token 签名秘钥
   */
  private static final String TOKEN_SECRET = "abc123";
  
  /**
   * refresh_token 签名秘钥
   */
  private static final String REFRESH_TOKEN_SECRET = "123abc";
  
  @NotNull
  public static String generateToken(String username) {
    return generateAnyToken(username, TOKEN_EXPIRE_TIME, TOKEN_SECRET);
  }
  
  @Nullable
  public static String getUsernameByToken(String token) {
    return getUsernameByAnyToken(token, TOKEN_SECRET);
  }
  
  @NotNull
  public static String generateRefreshToken(String username) {
    return generateAnyToken(username, REFRESH_TOKEN_EXPIRE_TIME, REFRESH_TOKEN_SECRET);
  }
  
  @Nullable
  public static String getUsernameByRefreshToken(String token) {
    return getUsernameByAnyToken(token, REFRESH_TOKEN_SECRET);
  }
  
  @NotNull
  private static String generateAnyToken(String username, long expireTime, String tokenSecret) {
    Date now = new Date();
    Date expireData = new Date(now.getTime() + expireTime);
    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(now)
      .setExpiration(expireData)
      .signWith(SignatureAlgorithm.HS512, tokenSecret)
      .compact();
  }
  
  @Nullable
  private static String getUsernameByAnyToken(String token, String tokenSecret) {
    if (token == null) return null;
    String realToken = token;
    if (token.startsWith("Bearer ")) {
      realToken = token.substring(7);
    }
    try {
      Claims claims = Jwts.parser()
        .setSigningKey(tokenSecret)
        .parseClaimsJws(realToken)
        .getBody();
      return claims.getSubject();
    } catch (Exception e) {
      return null;
    }
  }
}
