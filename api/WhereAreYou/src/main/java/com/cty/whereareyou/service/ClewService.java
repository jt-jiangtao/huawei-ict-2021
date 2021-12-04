package com.cty.whereareyou.service;

import com.cty.whereareyou.entity.clew.Clew;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 9:34
 */
public interface ClewService {

    Object provideClew(int fromUser, int toUser, String clew);

    Object insertNotification(int fromUser, int toUser, String clew, String type);

    boolean updateSendStatus(int id);

    boolean updateSeenStatus(int id);

    List<Clew> selectNotSendClew();

    List<Clew> selectUserNotification(int user);

    List<Clew> selectUserNotSendNotification(int user);

    Object unseenMessage(int user);
}
