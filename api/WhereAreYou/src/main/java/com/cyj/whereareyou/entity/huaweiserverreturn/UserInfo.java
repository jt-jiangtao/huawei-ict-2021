package com.cyj.whereareyou.entity.huaweiserverreturn;

import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 21:30
 */
@Data
public class UserInfo {
    private int id;
    private String displayName;
    private String displayNameFlag;
    private String headPictureURL;
    private String openID;

    public UserInfo(String displayName, String headPictureURL, String openID) {
        this.displayName = displayName;
        this.headPictureURL = headPictureURL;
        this.openID = openID;
    }

    public UserInfo() {
    }
}
