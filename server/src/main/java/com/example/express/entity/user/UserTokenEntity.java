package com.example.express.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 22:18
 */
@Data
@AllArgsConstructor
@TableName(value = "user_token")
public class UserTokenEntity {
  @TableId
  private String username;
  private String refreshToken;
}
