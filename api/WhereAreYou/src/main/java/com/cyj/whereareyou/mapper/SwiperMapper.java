package com.cyj.whereareyou.mapper;

import com.cyj.whereareyou.entity.swiper.FrontSwiper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/6 16:06
 */
@Mapper
public interface SwiperMapper{

    @Select("SELECT id, router, picture, description FROM swiper;")
    List<FrontSwiper> selectSwiper();

}
