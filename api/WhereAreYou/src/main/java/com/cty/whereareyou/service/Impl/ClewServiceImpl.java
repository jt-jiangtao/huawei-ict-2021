package com.cty.whereareyou.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cty.whereareyou.entity.clew.Clew;
import com.cty.whereareyou.mapper.ArticlesMapper;
import com.cty.whereareyou.mapper.ClewMapper;
import com.cty.whereareyou.service.ClewService;
import com.cty.whereareyou.service.websocket.WebSocketServer;
import com.cty.whereareyou.utils.UsernameUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        Clew clew1 = new Clew();
        int status = clewMapper.insertItem(fromUser, toUser, clew, "CLEW", clew1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        map.put("status", status >= 1 ? "200" : "400");
        map.put("id", String.valueOf(clew1.getId()));
        sqlSession.commit();

        JSONObject jsonObject = JSON.parseObject("{}");
        jsonObject.put("fromUser", fromUser);
        jsonObject.put("toUser", UsernameUtils.transformToUsername(toUser));
        jsonObject.put("content", clew);
        jsonObject.put("type", "NOTIFICATION");
        jsonObject.put("id", map.get("id"));
        jsonObject.put("n_type", "CLEW");
        try {
            WebSocketServer.sendInfo(jsonObject.toJSONString(), UsernameUtils.transformToUsername(toUser));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Object insertNotification(int fromUser, int toUser, String clew, String type) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClewMapper clewMapper = sqlSession.getMapper(ClewMapper.class);
        Map<String, String> map = new HashMap<>();
        Clew clew1 = new Clew();
        int status = clewMapper.insertItem(fromUser, toUser, clew, type, clew1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        map.put("status", status >= 1 ? "200" : "400");
        map.put("id", String.valueOf(clew1.getId()));
        sqlSession.commit();

        JSONObject jsonObject = JSON.parseObject("{}");
        jsonObject.put("fromUser", fromUser);
        jsonObject.put("toUser", UsernameUtils.transformToUsername(toUser));
        jsonObject.put("content", clew);
        jsonObject.put("type", "NOTIFICATION");
        jsonObject.put("id", map.get("id"));
        jsonObject.put("n_type", type);
        try {
            WebSocketServer.sendInfo(jsonObject.toJSONString(), UsernameUtils.transformToUsername(toUser));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    public boolean updateSeenStatus(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClewMapper clewMapper = sqlSession.getMapper(ClewMapper.class);
        int status = clewMapper.updateSeenStatus(id);
        sqlSession.commit();
        return status >= 1;
    }

    @Override
    public List<Clew> selectNotSendClew() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClewMapper clewMapper = sqlSession.getMapper(ClewMapper.class);
        return clewMapper.selectNotSendNotification();
    }

    @Override
    public List<Clew> selectUserNotification(int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClewMapper clewMapper = sqlSession.getMapper(ClewMapper.class);
        return clewMapper.selectUserNotification(user);
    }

    @Override
    public List<Clew> selectUserNotSendNotification(int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClewMapper clewMapper = sqlSession.getMapper(ClewMapper.class);
        return clewMapper.selectUserNotSendNotification(user);
    }
}
