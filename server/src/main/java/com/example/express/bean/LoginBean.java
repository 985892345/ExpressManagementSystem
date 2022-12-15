package com.example.express.bean;

import com.example.express.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * .
 *
 * @author 985892345
 * 2022/12/13 12:55
 */
@Data
@AllArgsConstructor
public class LoginBean {
  private UserEntity user;
  private TokenBean auth;
}
