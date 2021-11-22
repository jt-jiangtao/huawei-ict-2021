package com.cty.whereareyou.service;

import com.cty.whereareyou.entity.publish.*;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/14 23:07
 */
public interface PublishService {

    SimpleInfo selectUserSimpleInfo(int userId);

    LossSimpleInfo selectAllSimpleInfo();

    LossSimpleInfo selectByKey(String key);

    DetailLossInfo selectDetailLossById(int id);

    UserLossSimpleInfo selectAllSimpleInfoByUserOfFindChild(int id);

    UserLossSimpleInfo selectAllSimpleInfoByUserOfFindParent(int id);

    int moveToTrash(int id);

    int deleteFromTrash(int id);

    int moveFromTrashToNormalORBack(int id);

    Object collectAdd(String id, String event);

    Object collectRemove(String id, String event);

    Object isCollect(String id, String event);

    Object collectNumber(String id);

    Object collectSimpleInfoByUser(String id);

    List<Contact> selectContacts(int user);

    Object removeContact(int id, int userId);

    Object addContact(String name, String phone, String location, String userId, String relation);

    Object updateContact(String id, String name, String phone, String location, String userId, String relation);
}
