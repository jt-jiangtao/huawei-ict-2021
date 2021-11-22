package com.cty.whereareyou.mapper;

import com.cty.whereareyou.entity.publish.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 14:53
 */
@Mapper
public interface ImageMapper {

    @Insert("INSERT INTO images(user_id, image_url, `analyze`, loss_id) VALUES(#{user}, #{image}, #{analyze}, #{loss})")
    int insertImage(int user, String image, int analyze, int loss);

    @Select("SELECT * FROM images WHERE `analyze` = 0  AND loss_id != #{id};")
    List<Image> searchRawImage(int id);

    @Select("SELECT * FROM images WHERE loss_id != #{id};")
    List<Image> searchAllImageExceptSelf(int id);
}