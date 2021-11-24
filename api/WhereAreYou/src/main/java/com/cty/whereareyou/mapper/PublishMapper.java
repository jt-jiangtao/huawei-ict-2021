package com.cty.whereareyou.mapper;

import com.cty.whereareyou.entity.publish.*;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT id, name, phone, location, user_id, relation FROM contact WHERE user_id = #{id} AND `delete` = 0;")
    List<Contact> selectContactsByLossId(int id);

    @Select("SELECT * FROM find WHERE loss_id = #{id};")
    DetailLossInfo selectDetailInfoByLossId(int id);

    @Select("SELECT loss_id, age, loss_time, loss_location, name, sex, type, user_id FROM find WHERE loss_id = #{id} AND type = #{operation};")
    UserLossSimpleInfo selectAllSimpleInfoByUserOfFind(int id, int operation);

    @Update("UPDATE find SET `delete` = #{operation} WHERE loss_id = #{id};")
    int deleteInfo(int id, int operation);

    @Select("SELECT find.loss_id, age, loss_time, loss_location, name, sex, type, image_url FROM find, images WHERE find.loss_id = images.loss_id AND images.analyze = 0 AND CONCAT(IFNULL(`age`,''),IFNULL(`loss_time`,''),IFNULL(`loss_location`,''),IFNULL(`name`,''),IFNULL(`sex`,''),IFNULL(`detail_characters`,''),IFNULL(`case_detail`,''))  LIKE  #{key} ORDER BY loss_time;")
    List<LossSimpleInfo.DatabaseItem> selectByKeyWord(String key);

    @Insert("INSERT INTO event_collect(event_id, user_id) VALUES(#{event}, #{id});")
    int collectAdd(String id, String event);

    @Delete("DELETE FROM event_collect WHERE event_id = #{event} AND user_id = #{id};")
    int collectRemove(String id, String event);

    @Select("SELECT COUNT(id) FROM event_collect WHERE event_id = #{event} AND user_id = #{id};")
    int isCollect(String id, String event);

    @Select("SELECT COUNT(event_id) FROM event_collect WHERE user_id = #{id};")
    int collectNumber(String id);

    @Select("SELECT event_id FROM event_collect WHERE user_id = #{id};")
    List<Integer> getEvenIdByUserId(String id);

    @Select("SELECT find.loss_id, age, loss_time, loss_location, name, sex, type, image_url FROM find, images WHERE find.loss_id = images.loss_id AND images.analyze = 0 AND find.loss_id = #{id} ORDER BY loss_time;")
    LossSimpleInfo.DatabaseItem selectItemSimpleInfo(String id);

    @Select("SELECT * FROM contact WHERE user_id = #{user}  AND `delete` = 0;")
    List<Contact> selectContacts(int user);

    @Select("SELECT COUNT(id) AS number FROM contact WHERE user_id = #{id}  AND `delete` = 0;")
    int leavePhotoNumber(int user);

    @Select("SELECT COUNT(loss_id) AS number FROM find WHERE user_id = #{user};")
    int hasLossChild(int user);

    @Update("UPDATE contact SET `delete` = 1 WHERE id = #{id};")
    int removeContact(int id);

    @Insert("INSERT INTO contact(name, phone, location, user_id, relation) VALUES(#{name}, #{phone}, #{location} , #{userId}, #{relation});")
    int addContact(String name, String phone, String location, int userId, String relation);

    @Update("UPDATE contact SET name = #{name}, phone = #{phone}, location = #{location} , relation = #{relation} WHERE id = #{id} AND user_id = #{userId};")
    int updateContact(String id, String name, String phone, String location, String userId, String relation);

    @Insert("INSERT INTO find(age, loss_time, loss_location, report_police, name, sex, detail_characters, case_detail, type, user_id) VALUES (#{age}, #{lossTime}, #{lossLocation}, #{reportPolice}, #{name}, #{sex}, #{detailCharacters}, #{caseDetail}, #{type}, #{userId});")
    @Options(useGeneratedKeys = true, keyProperty = "item.lossId", keyColumn = "loss_id")
    int insertFindInfo(int age, String lossTime, String lossLocation,int reportPolice, String name, String sex, String detailCharacters, String caseDetail, int type, int userId, LossSimpleInfo.Item item);
}
