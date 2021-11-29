package com.cty.whereareyou.service.Impl;

import com.cty.whereareyou.entity.publish.Image;
import com.cty.whereareyou.service.CompareFaceService;
import com.cty.whereareyou.service.HuaweiInteractionService;
import com.cty.whereareyou.service.ImageService;
import com.cty.whereareyou.service.SearchService;
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
                Image image = new Image();
                log.info(image.getImageUrl() + " are compare......");
                double similarity = compareFaceService.compareFaceByUrlSolution(url, image.getImageUrl());
                log.info(image.getImageUrl() + " compare complete, similarity = " + similarity);
            }
        }).start();
        // TODO: 保存数据,推送消息
    }


}
