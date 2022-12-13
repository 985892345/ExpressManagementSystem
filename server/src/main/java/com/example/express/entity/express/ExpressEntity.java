package com.example.express.entity.express;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 15:05
 */
@TableName(value = "express")
public class ExpressEntity {
  
  @TableId
  private Integer expressId;
  
  private String text;
  
  private int userId;
}
