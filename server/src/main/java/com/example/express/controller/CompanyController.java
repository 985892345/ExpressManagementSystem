package com.example.express.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.express.bean.ResponseBean;
import com.example.express.entity.company.CompanyEntity;
import com.example.express.mapper.company.CompanyMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author 985892345
 * 2022/12/13 19:23
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
  
  private final CompanyMapper companyMapper;
  
  public CompanyController(CompanyMapper companyMapper) {
    this.companyMapper = companyMapper;
  }
  
  @GetMapping("/all")
  public ResponseBean<Page<CompanyEntity>> getALl(
    @RequestParam int current,
    @RequestParam int size
  ) {
    Page<CompanyEntity> page = new Page<>(current, size);
    Page<CompanyEntity> result = companyMapper.selectPage(page, null);
    return ResponseBean.success(result);
  }
  
  @GetMapping("find")
  public ResponseBean<Page<CompanyEntity>> find(
    @RequestParam String key,
    @RequestParam int current,
    @RequestParam int size
  ) {
    Page<CompanyEntity> page = new Page<>(current, size);
    QueryWrapper<CompanyEntity> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("company_id", key)
      .or().like("company", key)
      .or().like("address", key)
      .or().like("company_inf", key);
    return ResponseBean.success(companyMapper.selectPage(page, queryWrapper));
  }
}
