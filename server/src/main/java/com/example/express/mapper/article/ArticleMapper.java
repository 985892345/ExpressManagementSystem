package com.example.express.mapper.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.express.entity.article.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleEntity> {
}
