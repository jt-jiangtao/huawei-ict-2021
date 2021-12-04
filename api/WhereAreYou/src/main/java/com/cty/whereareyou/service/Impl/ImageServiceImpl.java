package com.cty.whereareyou.service.Impl;

import com.cty.whereareyou.entity.publish.Image;
import com.cty.whereareyou.mapper.ArticlesMapper;
import com.cty.whereareyou.mapper.ImageMapper;
import com.cty.whereareyou.service.ImageService;
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
 * @Date: 2021/11/22 14:54
 */
@Service
public class ImageServiceImpl implements ImageService {
    SqlSessionFactory sqlSessionFactory = null;

    public ImageServiceImpl() throws IOException {
        String resource = "config/mybatis-config.xml";
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 获取session工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Override
    public Object insertRawImage(int user, String image, int loss) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
        int num = imageMapper.insertImage(user, image, 0 , loss);
        sqlSession.commit();
        Map<String, Integer> map = new HashMap<>();
        map.put("status", num);
        return map;
    }

    @Override
    public Object insertGenerateImage(int user, String image, int loss) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
        int num = imageMapper.insertImage(user, image, 1 , loss);
        sqlSession.commit();
        Map<String, Integer> map = new HashMap<>();
        map.put("status", num);
        return map;
    }

    @Override
    public List<Image> searchRawImage(int id, int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
        return imageMapper.searchRawImage(id, user_id);
    }

    @Override
    public List<Image> searchAllImageExceptSelf(int id, int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
        return imageMapper.searchAllImageExceptSelf(id, user_id);
    }
}
