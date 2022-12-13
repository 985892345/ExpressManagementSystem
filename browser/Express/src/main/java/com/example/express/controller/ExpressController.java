package com.example.express.controller;

import com.example.express.bean.ResponseBean;
import com.example.express.entity.express.ExpressEntity;
import com.example.express.mapper.express.ExpressMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 15:10
 */
@RestController("/express")
public class ExpressController {
  
  private final ExpressMapper mExpressMapper;
  
  public ExpressController(ExpressMapper mExpressMapper) {
    this.mExpressMapper = mExpressMapper;
  }
  
  @GetMapping("/all")
  public ResponseBean<List<ExpressEntity>> getAll() {
    // 这里需要检查 token，是否是管理员
    return ResponseBean.success(mExpressMapper.selectList(null));
  }
}
