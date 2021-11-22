package com.cty.whereareyou.service;

import com.cty.whereareyou.entity.clew.Clew;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 9:34
 */
public interface ClewService {

    Object provideClew(int fromUser, int toUser, String clew);

    boolean updateSendStatus(int id);

    List<Clew> selectNotSendClew();

}
