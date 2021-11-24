package com.cty.whereareyou.service;

import com.cty.whereareyou.entity.user.InsertUserBack;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: jiangtao
 * @Date: 2021/11/16 22:02
 */
public interface UserService {

    Object login(String code);

    int existUser(String id);

    boolean updateUserInfo(String refreshToken, String displayName, String headPictureUrl, String openId);

    InsertUserBack insertUser(String refreshToken, String displayName, String headPictureUrl, String openId);

    Object verify(String token);
}
