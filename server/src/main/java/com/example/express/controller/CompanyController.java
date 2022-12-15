package com.example.express.controller;

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
  public ResponseBean<CompanyEntity> find(
    @RequestParam int companyId
  ) {
    return ResponseBean.success(companyMapper.selectById(companyId));
  }
}
