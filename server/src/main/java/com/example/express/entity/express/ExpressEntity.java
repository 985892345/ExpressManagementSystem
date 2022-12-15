package com.example.express.entity.express;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 15:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "express")
public class ExpressEntity {
  @TableId
  private Integer expressId;
  @Nullable
  private Integer articleId;
  @Nullable
  private double cost;
  @Nullable
  private Integer sentId;
  @Nullable
  private Integer receiveId;
  @Nullable
  private Integer companyId;
  @Nullable
  private String local;
  @Nullable
  private String origin;
  @Nullable
  private String dest;
}
