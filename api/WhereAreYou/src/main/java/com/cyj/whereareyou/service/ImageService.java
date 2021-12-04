package com.cyj.whereareyou.service;

import com.cyj.whereareyou.entity.publish.Image;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 14:54
 */
public interface ImageService {
    Object insertRawImage(int user, String image, int loss);

    Object insertGenerateImage(int user, String image, int loss);

    List<Image> searchRawImage(int id, int user_id);

    List<Image> searchAllImageExceptSelf(int id, int user_id);
}
