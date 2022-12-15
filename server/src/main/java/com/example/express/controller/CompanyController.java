package com.example.express.controller;

import com.example.express.bean.ResponseBean;
import com.example.express.entity.company.CompanyEntity;
import com.example.express.mapper.company.CompanyMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
  public ResponseBean<List<CompanyEntity>> getAll() {
    return ResponseBean.success(companyMapper.selectList(null));
  }
}
