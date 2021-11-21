package com.cty.whereareyou.service.Impl;

import com.cty.whereareyou.entity.huaweiserverreturn.TokenReturn;
import com.cty.whereareyou.entity.huaweiserverreturn.UserInfo;
import com.cty.whereareyou.mapper.UserMapper;
import com.cty.whereareyou.service.HuaweiInteractionService;
import com.cty.whereareyou.service.UserService;
import com.cty.whereareyou.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jiangtao
 * @Date: 2021/11/16 22:02
 */
@Service
public class UserServiceImpl implements UserService {
    SqlSessionFactory sqlSessionFactory = null;

    @Autowired
    private HuaweiInteractionService huaweiInteractionService;

    public UserServiceImpl() throws IOException {
        String resource = "config/mybatis-config.xml";
        // 读取配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 获取session工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Override
    public Object login(String code) {
        TokenReturn tokenReturn = huaweiInteractionService.getAccessToken(code);
        if (tokenReturn.getAccessToken() == null) return false;
        UserInfo userInfo = huaweiInteractionService.getUserInfo(tokenReturn.getAccessToken(), 1);
        if (userInfo.getOpenID() == null) return false;
        if (existUser(userInfo.getOpenID())){
            boolean status = updateUserInfo(tokenReturn.getRefreshToken(), userInfo.getDisplayName(), userInfo.getHeadPictureURL(), userInfo.getOpenID());
            if (!status) return false;
        }else {
            boolean status = insertUser(tokenReturn.getRefreshToken(), userInfo.getDisplayName(), userInfo.getHeadPictureURL(), userInfo.getOpenID());
            if (!status) return false;
        }
        String token = JWTUtils.createJWT(1000L * 60 * 60 * 24 * 180, userInfo.getOpenID(), userInfo.getHeadPictureURL(), userInfo.getDisplayName());
        Map<String,String> map = new HashMap<>();
        map.put("token", token);
        map.put("userId", userInfo.getOpenID());
        map.put("imageUrl", userInfo.getHeadPictureURL());
        map.put("username", userInfo.getDisplayName());
        return map;
    }

    @Override
    public boolean existUser(String id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int num = userMapper.existUser(id);
        if (num >= 1) return true;
        return false;
    }

    @Override
    public boolean updateUserInfo(String refreshToken, String displayName, String headPictureUrl, String openId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int status = userMapper.updateInfo(refreshToken, displayName, headPictureUrl, openId);
        sqlSession.commit();
        return status == 1;
    }

    @Override
    public boolean insertUser(String refreshToken, String displayName, String headPictureUrl, String openId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int status = userMapper.insertUser(refreshToken, displayName, headPictureUrl, openId);
        sqlSession.commit();
        return status == 1;
    }

    @Override
    public Object verify(String token) {
        Map<String, String> map = new HashMap<>();
        if (JWTUtils.isVerify(token)) {
            map.put("status", "200");
            Claims claims = JWTUtils.parseJWT(token);
            map.put("username", claims.get("username").toString());
            map.put("userId", claims.get("openId").toString());
            map.put("imageUrl", claims.get("imageUrl").toString());
        }
        else map.put("status", "400");
        return map;
    }

}
