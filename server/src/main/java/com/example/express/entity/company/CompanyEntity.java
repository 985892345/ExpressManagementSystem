package com.example.express.entity.company;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * .
 *
 * @author 985892345
 * 2022/12/13 19:40
 */
@Data
@AllArgsConstructor
@TableName(value = "company")
public class CompanyEntity {
  
  @TableId
  private Integer companyId;
}
