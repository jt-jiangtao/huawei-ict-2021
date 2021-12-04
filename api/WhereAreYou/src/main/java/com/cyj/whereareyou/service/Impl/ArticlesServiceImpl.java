package com.cyj.whereareyou.service.Impl;

import com.cyj.whereareyou.entity.article.Article;
import com.cyj.whereareyou.entity.article.ArticleNoContent;
import com.cyj.whereareyou.entity.article.FrontArticlesList;
import com.cyj.whereareyou.mapper.ArticlesMapper;
import com.cyj.whereareyou.service.ArticlesService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jiangtao
 * @Date: 2021/11/4 20:37
 */
@Service
public class ArticlesServiceImpl implements ArticlesService {
    SqlSessionFactory sqlSessionFactory = null;

    public ArticlesServiceImpl() throws IOException {
        String resource = "config/mybatis-config.xml";
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 获取session工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Override
    public FrontArticlesList selectArticlesSimpleIntroduceInfo() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        return new FrontArticlesList(articlesMapper.selectArticlesSimpleIntroduceInfo());
    }

    @Override
    public Article selectArticleById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        return articlesMapper.selectArticleById(id);
    }

    @Override
    public Object addLike(String article, int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();
        if (articlesMapper.isLike(article, user) <= 0){
            map.put("status", articlesMapper.addLike(article, user));
        }else {
            map.put("status", 0);
        }
        sqlSession.commit();
        return map;
    }

    @Override
    public Object removeLike(String article, int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();
        map.put("status", articlesMapper.removeLike(article, user));
        sqlSession.commit();
        return map;
    }

    @Override
    public Object isLike(String article, int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();
        map.put("status", articlesMapper.isLike(article, user));
        return map;
    }

    @Override
    public Object likeNumber(String article) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();
        map.put("number", articlesMapper.likeNumber(article));
        return map;
    }

    @Override
    public Object addCollect(String article, int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();
        if (articlesMapper.isCollect(article, user) <= 0){
            map.put("status", articlesMapper.addCollect(article, user));
        }else {
            map.put("status", 0);
        }
        sqlSession.commit();
        return map;
    }

    @Override
    public Object removeCollect(String article, int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();
        map.put("status", articlesMapper.removeCollect(article, user));
        sqlSession.commit();
        return map;
    }

    @Override
    public Object isCollect(String article, int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();
        map.put("status", articlesMapper.isCollect(article, user));
        return map;
    }

    @Override
    public Object collectNumber(String article) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();
        map.put("number", articlesMapper.collectNumber(article));
        return map;
    }

    @Override
    public Object getUserCollectNumber(int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();
        map.put("number", articlesMapper.getUserCollectNumber(user));
        return map;
    }

    @Override
    public Object getUserCollectInfo(int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        List<Integer> list = articlesMapper.getUserCollect(user);
        List<ArticleNoContent> list1 = new ArrayList<>();
        list.forEach(i -> {
            list1.add(articlesMapper.selectArticleSimpleInfoById(i));
        });
        return list1;
    }

    @Override
    public Object getArticleLikeCollectInfo(String article, int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ArticlesMapper articlesMapper = sqlSession.getMapper(ArticlesMapper.class);
        Map<String, Integer> map = new HashMap();

        map.put("is_like", articlesMapper.isLike(article, user));
        map.put("is_collect", articlesMapper.isCollect(article, user));

        map.put("like_num", articlesMapper.likeNumber(article));
        map.put("collect_num", articlesMapper.collectNumber(article));
        return map;
    }
}
