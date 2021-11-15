package com.cty.whereareyou.mapper;

import com.cty.whereareyou.entity.publish.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/14 23:09
 */
@Mapper
public interface PublishMapper {

    @Select("SELECT COUNT(CASE WHEN type = 0 THEN 1 END ) as find_children_number ,COUNT(CASE WHEN type = 1 THEN 1 END ) as find_parent_number FROM find WHERE user_id = #{userId};")
    SimpleInfo selectUserSimpleInfo(int userId);

    @Select("SELECT find.loss_id, age, loss_time, loss_location, name, sex, type, image_url FROM find, images WHERE find.loss_id = images.loss_id AND images.analyze = 0 ORDER BY loss_time;")
    List<LossSimpleInfo.DatabaseItem> selectAllSimpleInfo();

    @Select("SELECT id, image_url, `analyze` as type, loss_id FROM images WHERE loss_id = #{id} AND `analyze` = 0;")
    List<Image> selectImagesByLossId(int id);

    @Select("SELECT id, name, phone, location, user_id, relation FROM contact WHERE user_id = #{id};")
    List<Contact> selectContactsByLossId(int id);

    @Select("SELECT * FROM find WHERE loss_id = #{id};")
    DetailLossInfo selectDetailInfoByLossId(int id);

    @Select("SELECT loss_id, age, loss_time, loss_location, name, sex, type, user_id FROM find WHERE loss_id = #{id} AND type = #{operation};")
    UserLossSimpleInfo selectAllSimpleInfoByUserOfFind(int id, int operation);

    @Update("UPDATE find SET `delete` = #{operation} WHERE loss_id = #{id};")
    int deleteInfo(int id, int operation);
}
