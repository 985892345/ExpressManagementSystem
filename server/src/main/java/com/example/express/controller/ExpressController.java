package com.example.express.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.express.bean.ResponseBean;
import com.example.express.entity.express.ExpressEntity;
import com.example.express.mapper.express.ExpressMapper;
import com.example.express.utils.TokenUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
  public ResponseBean<Page<ExpressEntity>> find(
    @RequestHeader(value = "Authorization") String token,
    @RequestParam int expressId
  ) {
    return ResponseBean.success(null);
  }
}
