package com.cyj.whereareyou.mapper;

import com.cyj.whereareyou.entity.huaweiserverreturn.UserInfo;
import org.apache.ibatis.annotations.*;

/**
 * @Author: jiangtao
 * @Date: 2021/11/4 20:28
 */
@Mapper
public interface UserMapper {

    @Select("SELECT id as number FROM user WHERE open_id = #{id};")
    int existUser(String id);

    @Update("UPDATE user SET refresh_token =#{refreshToken}, display_name=#{displayName}, head_picture_url=#{headPictureUrl} WHERE open_id = #{openId};")
    int updateInfo(String refreshToken, String displayName, String headPictureUrl, String openId);

    @Insert("INSERT INTO user(open_id, refresh_token, display_name, head_picture_url) VALUES (#{openId},#{refreshToken},#{displayName},#{headPictureUrl});")
    @Options(useGeneratedKeys = true, keyProperty = "param.id", keyColumn = "id")
    int insertUser(String refreshToken, String displayName, String headPictureUrl, String openId,@Param("param") UserInfo userInfo);

}
