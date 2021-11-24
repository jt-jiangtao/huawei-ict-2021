package com.cty.whereareyou.service;

import com.cty.whereareyou.entity.publish.*;

import java.util.List;
import java.util.Map;

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

    Object addContact(String name, String phone, String location, int userId, String relation);

    Object updateContact(String id, String name, String phone, String location, String userId, String relation);

    Map<String,String> insertFindInfo(int age, String lossTime, String lossLocation, int reportPolice, String name, String sex, String detailCharacters, String caseDetail, int type, int userId, String images, String contacts);

    Object commitFindChild(int age, String lossTime, String lossLocation, int reportPolice, String name, String sex, String detailCharacters, String caseDetail, int userId, String images, String contacts);

    Object commitParent(int age, String lossTime, String lossLocation, int reportPolice, String name, String sex, String detailCharacters, String caseDetail, int userId, String images, String contacts);
}
