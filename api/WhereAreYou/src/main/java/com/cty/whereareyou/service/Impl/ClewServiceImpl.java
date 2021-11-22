package com.cty.whereareyou.service.Impl;

import com.cty.whereareyou.entity.clew.Clew;
import com.cty.whereareyou.mapper.ArticlesMapper;
import com.cty.whereareyou.mapper.ClewMapper;
import com.cty.whereareyou.service.ClewService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 9:34
 */
@Service
public class ClewServiceImpl implements ClewService {

    SqlSessionFactory sqlSessionFactory = null;

    public ClewServiceImpl() throws IOException {
        String resource = "config/mybatis-config.xml";
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 获取session工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Override
    public Object provideClew(int fromUser, int toUser, String clew) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClewMapper clewMapper = sqlSession.getMapper(ClewMapper.class);
        Map<String, String> map = new HashMap<>();
        map.put("status", clewMapper.insertItem(fromUser, toUser, clew) >= 1 ? "200" : "400");
        sqlSession.commit();
        return map;
    }

    @Override
    public boolean updateSendStatus(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClewMapper clewMapper = sqlSession.getMapper(ClewMapper.class);
        int status = clewMapper.updateSendStatus(id);
        sqlSession.commit();
        return status >= 1;
    }

    @Override
    public List<Clew> selectNotSendClew() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClewMapper clewMapper = sqlSession.getMapper(ClewMapper.class);
        return clewMapper.selectNotSendClew();
    }
}
