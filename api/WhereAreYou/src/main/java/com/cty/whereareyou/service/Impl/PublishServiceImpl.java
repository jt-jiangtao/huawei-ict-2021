package com.cty.whereareyou.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cty.whereareyou.entity.publish.*;
import com.cty.whereareyou.mapper.ArticlesMapper;
import com.cty.whereareyou.mapper.ImageMapper;
import com.cty.whereareyou.mapper.PublishMapper;
import com.cty.whereareyou.service.PublishService;
import com.cty.whereareyou.service.SearchService;
import com.cty.whereareyou.utils.UsernameUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @Author: jiangtao
 * @Date: 2021/11/14 23:08
 */
@Service
public class PublishServiceImpl implements PublishService {
    @Autowired
    private SearchService searchService;

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
    public LossSimpleInfo selectByKey(String key) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        List<LossSimpleInfo.DatabaseItem> items = publishMapper.selectByKeyWord("%" + key + "%");
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

    @Override
    public Object collectAdd(String id, String event) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        Map<String, Integer> map = new HashMap<>();
        if (publishMapper.isCollect(UsernameUtils.transformToId(id), event) <= 0){
            map.put("status", publishMapper.collectAdd(UsernameUtils.transformToId(id), event));
        }else {
            map.put("status", 0);
        }
        sqlSession.commit();
        return map;
    }

    @Override
    public Object collectRemove(String id, String event) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        Map<String, Integer> map = new HashMap<>();
        if (publishMapper.isCollect(UsernameUtils.transformToId(id), event) >= 1){
            map.put("status", publishMapper.collectRemove(UsernameUtils.transformToId(id), event));
        }else{
            map.put("status", 0);
        }
        sqlSession.commit();
        return map;
    }

    @Override
    public Object isCollect(String id, String event) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", publishMapper.isCollect(UsernameUtils.transformToId(id), event) >= 1 ? true : false);
        return map;
    }

    @Override
    public Object collectNumber(String id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        Map<String, Integer> map = new HashMap<>();
        map.put("status", publishMapper.collectNumber(UsernameUtils.transformToId(id)));
        return map;
    }

    @Override
    public Object collectSimpleInfoByUser(String id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        List<LossSimpleInfo> lossSimpleInfos = new ArrayList<>();
        List<LossSimpleInfo.DatabaseItem> databaseItems = new ArrayList<>();
        publishMapper.getEvenIdByUserId(UsernameUtils.transformToId(id)).forEach(i -> {
            databaseItems.add(publishMapper.selectItemSimpleInfo(i));
        });
        lossSimpleInfos.add(new LossSimpleInfo(databaseItems));
        return lossSimpleInfos;
    }

    @Override
    public List<Contact> selectContacts(int user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        return publishMapper.selectContacts(user);
    }

    @Override
    public Object removeContact(int id, int userId) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        Map<String, String> map = new HashMap<>();
        int leavePhotoNumber = publishMapper.leavePhotoNumber(userId);
        int userLossChildNumber = publishMapper.hasLossChild(userId);
        if (leavePhotoNumber >= 2 || userLossChildNumber <= 0) {
            publishMapper.removeContact(id);
            map.put("status", "200");
        }else {
            map.put("status","400");
        }
        sqlSession.commit();
        return map;
    }

    @Override
    public Object addContact(String name, String phone, String location, int userId, String relation) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        Map<String, String> map = new HashMap<>();
        map.put("status", publishMapper.addContact(name, phone, location, userId, relation) >= 1 ? "200" : "400");
        sqlSession.commit();
        return map;
    }

    @Override
    public Object updateContact(String id, String name, String phone, String location, int userId, String relation) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        Map<String, String> map = new HashMap<>();
        map.put("status", publishMapper.updateContact(id, name, phone, location, userId, relation) >= 1 ? "200" : "400");
        sqlSession.commit();
        return map;
    }

    @Override
    public Map<String, String> insertFindInfo(int age, String lossTime, String lossLocation, int reportPolice, String name, String sex, String detailCharacters, String caseDetail, int type, int userId, String images, String contacts) {
        Map<String, String> map = new HashMap<>();

        SqlSession sqlSession = sqlSessionFactory.openSession();
        PublishMapper publishMapper = sqlSession.getMapper(PublishMapper.class);
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);

        LossSimpleInfo.Item item = new LossSimpleInfo.Item();
        int publishStatus = publishMapper.insertFindInfo(age, lossTime, lossLocation, reportPolice, name, sex, detailCharacters, caseDetail, type, userId, item);
        if (publishStatus <= 0){
            map.put("status", "400");
            sqlSession.rollback();
            return map;
        }
        int lossId = item.getLossId();

        if (images.equals("")) images = "[]";
        List<String> imagesList = JSON.parseArray(images, String.class);
        for (int i = 0; i < imagesList.size(); i++) {
            int status = imageMapper.insertImage(userId, imagesList.get(i), 0, lossId);
            if (status <= 0){
                map.put("status", "400");
                sqlSession.rollback();
                return map;
            }
        }

        if (contacts.equals("")) contacts = "[]";
        List<Contact> contactList =JSON.parseArray(contacts, Contact.class);
        for (int i = 0; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);
            if (!contact.getPhone().equals("") && !contact.getPhone().isEmpty()) {
                int status = publishMapper.addContact(contact.getName(), contact.getPhone(), contact.getLocation(), userId, contact.getRelation());
                if (status <= 0){
                    map.put("status", "400");
                    sqlSession.rollback();
                    return map;
                }
            }
        }
        map.put("status", "200");
        map.put("loss_id", String.valueOf(lossId));
        sqlSession.commit();
        return map;
    }

    @Override
    public Object commitFindChild(int age, String lossTime, String lossLocation, int reportPolice, String name, String sex, String detailCharacters, String caseDetail, int userId, String images, String contacts) {
        Map<String, String> map = new HashMap<>();
        map = insertFindInfo(age, lossTime, lossLocation, reportPolice, name, sex, detailCharacters, caseDetail, 0 , userId, images, contacts);
//        if (map.get("status").equals("200"))  // TODO: 调用接口生成图片
        return map;
    }

    @Override
    public Object commitParent(int age, String lossTime, String lossLocation, int reportPolice, String name, String sex, String detailCharacters, String caseDetail, int userId, String images, String contacts) {
        Map<String, String> map = new HashMap<>();
        map = insertFindInfo(age, lossTime, lossLocation, reportPolice, name, sex, detailCharacters, caseDetail, 1, userId, images, contacts);
        int lossId = Integer.parseInt(map.get("loss_id"));
        if (map.get("status").equals("200")){
            List<String> imagesList = JSON.parseArray(images, String.class);
            imagesList.forEach(s -> {
                searchService.findParent(s, lossId);
            });
        }
        return map;
    }
}
