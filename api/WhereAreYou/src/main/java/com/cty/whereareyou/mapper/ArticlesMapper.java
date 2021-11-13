package com.cty.whereareyou.mapper;

import com.cty.whereareyou.entity.article.Article;
import com.cty.whereareyou.entity.article.FrontArticlesList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/4 20:28
 */
@Mapper
public interface ArticlesMapper {

    @Select("SELECT sort_id, articles.id AS article_id, article_sort.title AS sort_title, articles.title AS article_title ,author, head_picture  FROM article_sort,articles WHERE article_sort.id = articles.sort_id;")
    List<FrontArticlesList.SelectItem> selectArticlesSimpleIntroduceInfo();

    @Select("SELECT * FROM articles WHERE id = #{id};")
    Article selectArticleById(int id);
}
