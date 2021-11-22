package com.cty.whereareyou.mapper;

import com.cty.whereareyou.entity.clew.Clew;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 9:32
 */
@Mapper
public interface ClewMapper {

    @Insert("INSERT INTO clew(from_user, to_user, clew) VALUES(#{fromUser}, #{toUser}, #{clew});")
    int insertItem(int fromUser, int toUser, String clew);

    @Update("UPDATE clew SET send = 1 WHERE id = #{id};")
    int updateSendStatus(int id);

    @Select("SELECT * FROM clew WHERE send = 0;")
    List<Clew> selectNotSendClew();

}
