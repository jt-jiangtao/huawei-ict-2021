package com.cty.whereareyou.mapper;

import com.cty.whereareyou.entity.article.Article;
import com.cty.whereareyou.entity.article.FrontArticlesList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/4 20:28
 */
@Mapper
public interface UserMapper {

    @Select("SELECT COUNT(open_id) as number FROM user WHERE open_id = #{id};")
    int existUser(String id);

    @Update("UPDATE user SET refresh_token =#{refreshToken}, display_name=#{displayName}, head_picture_url=#{headPictureUrl} WHERE open_id = #{openId};")
    int updateInfo(String refreshToken, String displayName, String headPictureUrl, String openId);

    @Insert("INSERT INTO user(open_id, refresh_token, display_name, head_picture_url) VALUES (#{openId},#{refreshToken},#{displayName},#{headPictureUrl});")
    int insertUser(String refreshToken, String displayName, String headPictureUrl, String openId);
}
