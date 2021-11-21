package com.cty.whereareyou.mapper;

import com.cty.whereareyou.entity.article.Article;
import com.cty.whereareyou.entity.article.ArticleNoContent;
import com.cty.whereareyou.entity.article.FrontArticlesList;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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

    @Insert("INSERT INTO article_likes(article_id, user_id) VALUES (#{article}, #{user});")
    int addLike(String article, String user);

    @Delete("DELETE FROM article_likes WHERE article_id = #{article} AND user_id = #{user};")
    int removeLike(String article, String user);

    @Select("SELECT COUNT(like_id) as number FROM article_likes WHERE article_id = #{article} AND user_id = #{user};")
    int isLike(String article, String user);

    @Select("SELECT COUNT(like_id) as number FROM article_likes WHERE article_id = #{article};")
    int likeNumber(String article);

    @Insert("INSERT INTO article_collect(article_id, user_id) VALUES(#{article}, #{user});")
    int addCollect(String article, String user);

    @Delete("DELETE FROM article_collect WHERE article_id = #{article} AND user_id = #{user};")
    int removeCollect(String article, String user);

    @Select("SELECT COUNT(id) as number FROM article_collect WHERE article_id = #{article} AND user_id = #{user};")
    int isCollect(String article, String user);

    @Select("SELECT COUNT(id) as number FROM article_collect WHERE article_id = #{article};")
    int collectNumber(String article);

    @Select("SELECT COUNT(id) as number FROM article_collect WHERE user_id = #{user};")
    int getUserCollectNumber(String user);

    @Select("SELECT article_id as number FROM article_collect WHERE user_id = #{user};")
    List<Integer> getUserCollect(String user);

    @Select("SELECT id, sort_id, title, author, head_picture FROM articles WHERE id = #{id};")
    ArticleNoContent selectArticleSimpleInfoById(int id);
}
