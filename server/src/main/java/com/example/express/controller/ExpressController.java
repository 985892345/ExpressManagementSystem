package com.example.express.controller;

import com.example.express.bean.ResponseBean;
import com.example.express.entity.express.ExpressEntity;
import com.example.express.mapper.express.ExpressMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 15:10
 */
@RestController
@RequestMapping(value = "/express")
public class ExpressController {
  
  private final ExpressMapper expressMapper;
  
  public ExpressController(ExpressMapper expressMapper) {
    this.expressMapper = expressMapper;
  }
  
  @GetMapping("/all")
  public ResponseBean<List<ExpressEntity>> getAll() {
    // 这里正常情况需要检查 token，是否是管理员，但检查操作交给了拦截器
    return ResponseBean.success(expressMapper.selectList(null));
  }
  
  @GetMapping("/find")
  public ResponseBean<ExpressEntity> find(
    @RequestParam int expressId
  ) {
    return ResponseBean.success(expressMapper.selectById(expressId));
  }
}
