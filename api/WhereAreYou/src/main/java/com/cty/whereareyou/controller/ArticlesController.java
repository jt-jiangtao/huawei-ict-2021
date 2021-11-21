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

    @GetMapping("/like/add")
    public Object addLike(String article, String user) {
        return articlesService.addLike(article, user);
    }

    @GetMapping("/like/remove")
    public Object removeLike(String article, String user) {
        return articlesService.removeLike(article, user);
    }

    @GetMapping("/isLike")
    public Object isLike(String article, String user) {
        return articlesService.isLike(article, user);
    }

    @GetMapping("/like/number")
    public Object likeNumber(String article) {
        return articlesService.likeNumber(article);
    }

    @GetMapping("/collect/add")
    public Object addCollect(String article, String user) {
        return articlesService.addCollect(article, user);
    }

    @GetMapping("/collect/remove")
    public Object removeCollect(String article, String user) {
        return articlesService.removeCollect(article, user);
    }

    @GetMapping("/isCollect")
    public Object isCollect(String article, String user) {
        return articlesService.isCollect(article, user);
    }

    @GetMapping("/collect/number")
    public Object collectNumber(String article) {
        return articlesService.collectNumber(article);
    }

    @GetMapping("/collect/user/number")
    public Object getUserCollectNumber(String user) {
        return articlesService.getUserCollectNumber(user);
    }

    @GetMapping("/collect/user/info")
    public Object getUserCollectInfo(String user) {
        return articlesService.getUserCollectInfo(user);
    }

    @GetMapping("/article/lc_info")
    public Object getArticleLikeCollectInfo(String article, String user){
        return articlesService.getArticleLikeCollectInfo(article, user);
    }
}
