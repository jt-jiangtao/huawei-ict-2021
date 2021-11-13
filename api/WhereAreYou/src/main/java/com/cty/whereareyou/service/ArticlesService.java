package com.cty.whereareyou.service;

import com.cty.whereareyou.entity.article.Article;
import com.cty.whereareyou.entity.article.FrontArticlesList;

/**
 * @Author: jiangtao
 * @Date: 2021/11/4 20:37
 */
public interface ArticlesService {

    FrontArticlesList selectArticlesSimpleIntroduceInfo();

    Article selectArticleById(int id);
}
