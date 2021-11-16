package com.cty.whereareyou.entity.huaweiserverreturn;

import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 21:28
 */
@Data
public class TokenReturn {
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private String scope;
    private String tokenType;
    private String idToken;

    public TokenReturn(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
