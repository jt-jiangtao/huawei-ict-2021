package com.cty.whereareyou.entity.user;

import lombok.Data;

/**
 * @Author: jiangtao
 * @Date: 2021/11/23 13:12
 */
@Data
public class InsertUserBack {
    private int id;
    private boolean status;

    public int getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }

    public InsertUserBack(int id, boolean status) {
        this.id = id;
        this.status = status;
    }
}
