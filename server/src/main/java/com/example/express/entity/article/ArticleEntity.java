package com.example.express.entity.article;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName(value = "article")
public class ArticleEntity {
    @TableId
    private Integer articleId;
    private String article;
    private double cost;
    private String articleInf;
}
