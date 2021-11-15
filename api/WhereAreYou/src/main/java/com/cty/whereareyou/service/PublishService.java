package com.cty.whereareyou.service;

import com.cty.whereareyou.entity.publish.DetailLossInfo;
import com.cty.whereareyou.entity.publish.LossSimpleInfo;
import com.cty.whereareyou.entity.publish.SimpleInfo;
import com.cty.whereareyou.entity.publish.UserLossSimpleInfo;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/14 23:07
 */
public interface PublishService {

    SimpleInfo selectUserSimpleInfo(int userId);

    LossSimpleInfo selectAllSimpleInfo();

    DetailLossInfo selectDetailLossById(int id);

    UserLossSimpleInfo selectAllSimpleInfoByUserOfFindChild(int id);

    UserLossSimpleInfo selectAllSimpleInfoByUserOfFindParent(int id);

    int moveToTrash(int id);

    int deleteFromTrash(int id);

    int moveFromTrashToNormalORBack(int id);
}
