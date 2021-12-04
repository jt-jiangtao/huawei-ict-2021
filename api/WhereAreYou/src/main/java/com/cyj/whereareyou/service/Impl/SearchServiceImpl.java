package com.cyj.whereareyou.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyj.whereareyou.entity.publish.Image;
import com.cyj.whereareyou.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
            List<Image> images = imageService.searchRawImage(id, publishService.findUserIdByLossId(id));
            List<Image> possibleImages = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                Image image = images.get(i);
                log.info(image.getImageUrl() + " are compare......");
                double similarity = compareFaceService.compareFaceByUrlSolution(url, image.getImageUrl());
                log.info(image.getImageUrl() + " compare complete, similarity = " + similarity);
                if (similarity >= 0.93){
                    // 给找父母一方发送消息
                    sendToFindParent(similarity, image, id, url, true, 0);
                    // 给找孩子一方发送消息
                    sendToFindChild(similarity, image, id, url, true, 0);
                    break;
                }else if (similarity >= 0.9){
                    // 可能匹配上,发送通知消息
                    image.setSimilarity(similarity);
                    possibleImages.add(image);
                }
            }
            // 排序,找出0.9-0.93之间最大的
            Collections.sort(possibleImages, new Comparator<Image>() {
                @Override
                public int compare(Image o1, Image o2) {
                    return (int) (o2.getSimilarity() - o1.getSimilarity());
                }
            });
            if (! possibleImages.isEmpty()){
                sendToFindParent(possibleImages.get(0).getSimilarity(), possibleImages.get(0), id, url, false, 0);
                sendToFindChild(possibleImages.get(0).getSimilarity(), possibleImages.get(0), id, url, false, 0);
            }
        }).start();
    }

    @Override
    public void findParent(String url, int id) {
        new Thread(()->{
            List<Image> images = imageService.searchAllImageExceptSelf(id, publishService.findUserIdByLossId(id));
            List<Image> possibleImages = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                Image image = images.get(i);
                log.info(image.getImageUrl() + " are compare......");
                double similarity = compareFaceService.compareFaceByUrlSolution(url, image.getImageUrl());
                log.info(image.getImageUrl() + " compare complete, similarity = " + similarity);
                if (similarity >= 0.93){
                    // 给找父母一方发送消息
                    sendToFindParent(similarity, image, id, url, true, 1);
                    // 给找孩子一方发送消息
                    sendToFindChild(similarity, image, id, url, true, 1);
                    break;
                }else if (similarity >= 0.9){
                    // 可能匹配上,发送通知消息
                    image.setSimilarity(similarity);
                    possibleImages.add(image);
                }
            }
            // 排序,找出0.9-0.93之间最大的
            Collections.sort(possibleImages, new Comparator<Image>() {
                @Override
                public int compare(Image o1, Image o2) {
                    return (int) (o2.getSimilarity() - o1.getSimilarity());
                }
            });
            if (possibleImages.isEmpty()){
                sendEmpty(id);
            }else {
                sendToFindParent(possibleImages.get(0).getSimilarity(), possibleImages.get(0), id, url, false, 1);
                sendToFindChild(possibleImages.get(0).getSimilarity(), possibleImages.get(0), id, url, false, 1);
            }
        }).start();
    }

    private void sendEmpty(int id) {
        JSONObject jsonObjectC = JSON.parseObject("{}");
        jsonObjectC.put("content", "对比失败。系统将不断对比数据库,请耐心等待。祝愿早日找到父母！！！");
        clewService.insertNotification(-1, publishService.findUserIdByLossId(id), jsonObjectC.toJSONString(), "FIND");
    }

    // 将自己信息发给对方
    private void sendToFindChild(double similarity, Image image, int id, String url, boolean status, int parent) {
        JSONObject jsonObjectC = JSON.parseObject("{}");
        String name = "孩子";
        if (parent == 0) name = "父母";
        if (status){
            jsonObjectC.put("content", "查找成功！！人脸对比相似率为" + similarity + ", 请联系对方确认。同时，系统将向双方提供联系方式。祝愿早日找到" + name + "！！！");
        }else {
            jsonObjectC.put("content", "系统查找到相似度较高的照片，请联系对方确认。同时，系统将向双方提供联系方式。祝愿早日找到" + name + "！！！");
        }
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
        jsonObjectC.put("loss", id);
        clewService.insertNotification(-1, image.getUserId(), jsonObjectC.toJSONString(), "FIND");
    }

    // 将对方信息发给自己
    private void sendToFindParent(double similarity, Image image, int id, String url, boolean status,int parent) {
        JSONObject jsonObjectC = JSON.parseObject("{}");
        String name = "父母";
        if (parent == 0) name = "孩子";
        if (status){
            jsonObjectC.put("content", "查找成功！！人脸对比相似率为" + similarity + ", 请联系对方确认。同时，系统将向双方提供联系方式。祝愿早日找到" + name + "！！！");
        }else {
            jsonObjectC.put("content", "系统查找到相似度较高的照片，请联系对方确认。同时，系统将向双方提供联系方式。祝愿早日找到" + name + "！！！");
        }
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
        jsonObjectC.put("loss", image.getLossId());
        clewService.insertNotification(-1, publishService.findUserIdByLossId(id), jsonObjectC.toJSONString(), "FIND");
    }


}
