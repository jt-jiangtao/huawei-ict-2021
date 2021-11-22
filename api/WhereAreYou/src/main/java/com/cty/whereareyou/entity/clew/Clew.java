package com.cty.whereareyou.entity.clew;

import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 20:36
 */
@Data
public class Clew {
    private int id;
    private int fromUser;
    private int toUser;
    private String clew;
}
