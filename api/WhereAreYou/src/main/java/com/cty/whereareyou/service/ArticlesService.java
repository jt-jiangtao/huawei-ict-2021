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

    Object addLike(String article, String user);

    Object removeLike(String article, String user);

    Object isLike(String article, String user);

    Object likeNumber(String article);

    Object addCollect(String article, String user);

    Object removeCollect(String article, String user);

    Object isCollect(String article, String user);

    Object collectNumber(String article);

    Object getUserCollectNumber(String user);

    Object getUserCollectInfo(String user);

    Object getArticleLikeCollectInfo(String article, String user);
}
