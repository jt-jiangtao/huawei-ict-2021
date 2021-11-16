package com.cty.whereareyou.service;

import com.cty.whereareyou.entity.huaweiserverreturn.TokenReturn;
import com.cty.whereareyou.entity.huaweiserverreturn.UserInfo;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 18:58
 */
public interface HuaweiInteractionService {

    public TokenReturn getAccessToken(String code);

    public TokenReturn refreshToken(String refreshToken);

    public UserInfo getUserInfo(String accessToken, int getNickName);
}
