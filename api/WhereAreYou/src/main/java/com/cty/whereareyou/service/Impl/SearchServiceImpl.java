package com.cty.whereareyou.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cty.whereareyou.entity.publish.Contact;
import com.cty.whereareyou.entity.publish.Image;
import com.cty.whereareyou.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 15:22
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ImageService imageService;

    @Autowired
    private CompareFaceService compareFaceService;

    @Autowired
    private ClewService clewService;

    @Autowired
    private PublishService publishService;

    @Override
    public void findChild(String url, int id) {
        new Thread(() -> {
            List<Image> images = imageService.searchRawImage(id);
            images.forEach(image -> {
                log.info(image.getImageUrl() + " are compare......");
                double similarity = compareFaceService.compareFaceByUrlSolution(url, image.getImageUrl());
                log.info(image.getImageUrl() + " compare complete, similarity = " + similarity);
            });
        }).start();
        // TODO: 保存数据,推送消息
    }

    @Override
    public void findParent(String url, int id) {
        new Thread(()->{
            List<Image> images = imageService.searchAllImageExceptSelf(id);
            for (int i = 0; i < images.size(); i++) {
                Image image = images.get(i);
                log.info(image.getImageUrl() + " are compare......");
                double similarity = compareFaceService.compareFaceByUrlSolution(url, image.getImageUrl());
                log.info(image.getImageUrl() + " compare complete, similarity = " + similarity);
                if (similarity >= 0.93){
                    // 给找父母一方发送消息
                    sendToFindParent(similarity, image, id, url);
                    // 给找孩子一方发送消息
                    sendToFindChild(similarity, image, id, url);
                    break;
                }else if (similarity >= 0.9){
                    // 可能匹配上,发送通知消息
                }
            }
        }).start();
    }

    private void sendToFindChild(double similarity, Image image, int id, String url) {
        JSONObject jsonObjectC = JSON.parseObject("{}");
        jsonObjectC.put("content", "查找成功！！人脸对比相似率为" + similarity + ", 请联系对方确认。同时，系统将向双方提供联系方式。祝愿早日找到孩子！！！");
        JSONArray contacts = new JSONArray();
        publishService.selectContacts(publishService.findUserIdByLossId(id)).forEach(contact -> {
            JSONObject jb = JSON.parseObject("{}");
            jb.put("id", contact.getId());
            jb.put("name", contact.getName());
            jb.put("phone", contact.getPhone());
            jb.put("location", contact.getLocation());
            jb.put("relation", contact.getRelation());
            contacts.add(jb);
        });
        JSONArray images = new JSONArray();
        images.add(image.getImageUrl());
        images.add(url);
        jsonObjectC.put("contacts", contacts);
        jsonObjectC.put("images", images);
        clewService.insertNotification(-1, image.getUserId(), jsonObjectC.toJSONString(), "FIND");
    }

    private void sendToFindParent(double similarity, Image image, int id, String url) {
        JSONObject jsonObjectC = JSON.parseObject("{}");
        jsonObjectC.put("content", "查找成功！！人脸对比相似率为" + similarity + ", 请联系对方确认。同时，系统将向双方提供联系方式。祝愿早日找到父母！！！");
        JSONArray contacts = new JSONArray();
        publishService.selectContacts(image.getUserId()).forEach(contact -> {
            JSONObject jb = JSON.parseObject("{}");
            jb.put("id", contact.getId());
            jb.put("name", contact.getName());
            jb.put("phone", contact.getPhone());
            jb.put("location", contact.getLocation());
            jb.put("relation", contact.getRelation());
            contacts.add(jb);
        });
        JSONArray images = new JSONArray();
        images.add(url);
        images.add(image.getImageUrl());
        jsonObjectC.put("contacts", contacts);
        jsonObjectC.put("images", images);
        clewService.insertNotification(-1, publishService.findUserIdByLossId(id), jsonObjectC.toJSONString(), "FIND");
    }


}
