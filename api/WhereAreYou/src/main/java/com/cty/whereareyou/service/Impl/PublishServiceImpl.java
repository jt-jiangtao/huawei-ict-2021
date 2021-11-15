package com.cty.whereareyou.service.Impl;

import com.cty.whereareyou.entity.publish.*;
import com.cty.whereareyou.mapper.ArticlesMapper;
import com.cty.whereareyou.mapper.PublishMapper;
import com.cty.whereareyou.service.PublishService;
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
 * @Date: 2021/11/14 23:08
 */
@Service
public class PublishServiceImpl implements PublishService {
    SqlSessionFactory sqlSessionFactory = null;

    public PublishServiceImpl() throws IOException {
        String resource = "config/mybatis-config.xml";
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 获取session工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Override
    public SimpleInfo selectUserSimpleInfo(int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        return publishMapper.selectUserSimpleInfo(userId);
    }

    @Override
    public LossSimpleInfo selectAllSimpleInfo() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        List<LossSimpleInfo.DatabaseItem> items = publishMapper.selectAllSimpleInfo();
        return new LossSimpleInfo(items);
    }

    @Override
    public DetailLossInfo selectDetailLossById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        DetailLossInfo detailLossInfo = publishMapper.selectDetailInfoByLossId(id);
        if (detailLossInfo == null) return new DetailLossInfo();
        List<Image> images = publishMapper.selectImagesByLossId(id);
        detailLossInfo.setImages(images);
        List<Contact> contacts = publishMapper.selectContactsByLossId(detailLossInfo.getUserId());
        detailLossInfo.setContacts(contacts);
        return detailLossInfo;
    }

    @Override
    public UserLossSimpleInfo selectAllSimpleInfoByUserOfFindChild(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        UserLossSimpleInfo userLossSimpleInfo = publishMapper.selectAllSimpleInfoByUserOfFind(id, 0);
        if (userLossSimpleInfo == null) return new UserLossSimpleInfo();
        List<Image> images = publishMapper.selectImagesByLossId(id);
        userLossSimpleInfo.setImages(images);
        return userLossSimpleInfo;
    }

    @Override
    public UserLossSimpleInfo selectAllSimpleInfoByUserOfFindParent(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        UserLossSimpleInfo userLossSimpleInfo = publishMapper.selectAllSimpleInfoByUserOfFind(id, 1);
        if (userLossSimpleInfo == null) return new UserLossSimpleInfo();
        List<Image> images = publishMapper.selectImagesByLossId(id);
        userLossSimpleInfo.setImages(images);
        return userLossSimpleInfo;
    }

    @Override
    public int moveToTrash(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        int code = publishMapper.deleteInfo(id, 1);
        sqlSession.commit();
        return code;
    }

    @Override
    public int deleteFromTrash(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        int code = publishMapper.deleteInfo(id, 2);
        sqlSession.commit();
        return code;
    }

    @Override
    public int moveFromTrashToNormalORBack(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        int code = publishMapper.deleteInfo(id, 0);
        sqlSession.commit();
        return code;
    }
}
