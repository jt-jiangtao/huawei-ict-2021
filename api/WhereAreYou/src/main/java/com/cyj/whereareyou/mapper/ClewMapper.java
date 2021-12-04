package com.cyj.whereareyou.mapper;

import com.cyj.whereareyou.entity.clew.Clew;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 9:32
 */
@Mapper
public interface ClewMapper {

    @Insert("INSERT INTO notification(from_user, to_user, content, type, time) VALUES(#{fromUser}, #{toUser}, #{clew}, #{type}, #{time});")
    @Options(useGeneratedKeys = true, keyProperty = "param.id", keyColumn = "id")
    int insertItem(int fromUser, int toUser, String clew, String type, Clew param, String time);

    @Update("UPDATE notification SET send = 1 WHERE id = #{id};")
    int updateSendStatus(int id);

    @Update("UPDATE notification SET seen = 1 WHERE id = #{id};")
    int updateSeenStatus(int id);

    @Select("SELECT * from notification WHERE to_user = #{user} ORDER BY time DESC;")
    List<Clew> selectUserNotification(int user);

    @Select("SELECT * FROM notification WHERE send = 0;")
    List<Clew> selectNotSendNotification();

    @Select("SELECT * FROM notification WHERE send = 0 AND to_user = #{user};")
    List<Clew> selectUserNotSendNotification(int user);

    @Select("SELECT COUNT(id) as number FROM notification WHERE to_user = #{user} AND seen = 0;")
    int unseenMessage(int user);
}
