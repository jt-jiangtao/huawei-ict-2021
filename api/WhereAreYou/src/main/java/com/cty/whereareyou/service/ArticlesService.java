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

    Object addLike(String article, int user);

    Object removeLike(String article, int user);

    Object isLike(String article, int user);

    Object likeNumber(String article);

    Object addCollect(String article, int user);

    Object removeCollect(String article, int user);

    Object isCollect(String article, int user);

    Object collectNumber(String article);

    Object getUserCollectNumber(int user);

    Object getUserCollectInfo(int user);

    Object getArticleLikeCollectInfo(String article, int user);
}
