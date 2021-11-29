package com.cty.whereareyou.utils;

/**
 * @Author: jiangtao
 * @Date: 2021/11/24 19:19
 */
public class UsernameUtils {

    public static int transformToId(String username) {
        if (username.startsWith("visitor@")){
            return 0;
        }
        return Integer.parseInt(username.split("@")[1]) - 10000000;
    }

    public static String transformToUsername(int id) {
        int mapId = 10000000 + id;
        return "user@" + mapId;
    }
}
