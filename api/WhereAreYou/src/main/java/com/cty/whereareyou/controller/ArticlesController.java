package com.cty.whereareyou.controller;

import com.cty.whereareyou.core.UnifyResponse;
import com.cty.whereareyou.entity.article.Article;
import com.cty.whereareyou.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: jiangtao
 * @Date: 2021/11/4 20:31
 */
@RestController
@CrossOrigin
@RequestMapping("/articles")
public class ArticlesController {
    @Autowired
    private ArticlesService articlesService;

    @GetMapping("/get_simple_info")
    public Object getSimpleInfo() {
        return articlesService.selectArticlesSimpleIntroduceInfo();
    }

    @GetMapping("/get/{id}")
    public Object getArticleById(@PathVariable("id") Integer id) {
        Article article = articlesService.selectArticleById(id);
        if (article == null) return UnifyResponse.build(11201);
        return article;
    }
}
