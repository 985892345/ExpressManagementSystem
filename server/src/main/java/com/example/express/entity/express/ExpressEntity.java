package com.example.express.entity.express;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 15:05
 */
@Data
@AllArgsConstructor
@TableName(value = "express")
public class ExpressEntity {
  @TableId
  private Integer expressId;
  private Integer articleId;
  private double cost;
  private Timestamp time;
  private Integer sentId;
  private Integer receiveId;
  private Integer companyId;
  private String local;
  private String origin;
  private String dest;
}
