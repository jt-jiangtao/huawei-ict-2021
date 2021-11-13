package com.cty.whereareyou.service.Impl;

import com.cty.whereareyou.entity.swiper.FrontSwiper;
import com.cty.whereareyou.mapper.ArticlesMapper;
import com.cty.whereareyou.mapper.SwiperMapper;
import com.cty.whereareyou.service.SwiperService;
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
 * @Date: 2021/11/6 16:10
 */
@Service
public class SwiperServiceImpl implements SwiperService {
    SqlSessionFactory sqlSessionFactory = null;

    public SwiperServiceImpl() throws IOException {
        String resource = "config/mybatis-config.xml";
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 获取session工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Override
    public List<FrontSwiper> getSwiperInfo() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SwiperMapper swiperMapper = sqlSession.getMapper(SwiperMapper.class);
        return swiperMapper.selectSwiper();
    }
}
