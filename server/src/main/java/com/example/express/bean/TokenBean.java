package com.example.express.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 20:15
 */
@Data
@AllArgsConstructor
public class TokenBean {
  private String token;
  private String refreshToken;
}
