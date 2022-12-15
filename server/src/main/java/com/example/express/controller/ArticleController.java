package com.example.express.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.express.bean.ResponseBean;
import com.example.express.entity.article.ArticleEntity;
import com.example.express.entity.company.CompanyEntity;
import com.example.express.entity.express.ExpressEntity;
import com.example.express.mapper.article.ArticleMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleMapper articleMapper;

    public ArticleController(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @GetMapping("/all")
    public ResponseBean<Page<ArticleEntity>> getAll(
            @RequestParam int current,
            @RequestParam int size
    ) {
        // 这里正常情况需要检查 token，是否是管理员，但检查操作交给了拦截器
        Page<ArticleEntity> page = new Page<>(current,size);
        Page<ArticleEntity> result = articleMapper.selectPage(page,null);
        return ResponseBean.success(result);
    }

    @GetMapping("/find")
    public ResponseBean<Page<ArticleEntity>> find(
        @RequestParam String key,
        @RequestParam int current,
        @RequestParam int size
    ) {
        Page<ArticleEntity> page = new Page<>(current, size);
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("article_id", key)
          .or().like("article", key)
          .or().like("article_inf", key);
        return ResponseBean.success(articleMapper.selectPage(page, queryWrapper));
    }

}
