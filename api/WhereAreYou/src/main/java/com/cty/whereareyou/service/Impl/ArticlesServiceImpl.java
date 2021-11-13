package com.cty.whereareyou.service.Impl;

import com.cty.whereareyou.entity.article.Article;
import com.cty.whereareyou.entity.article.FrontArticlesList;
import com.cty.whereareyou.mapper.ArticlesMapper;
import com.cty.whereareyou.service.ArticlesService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
}
