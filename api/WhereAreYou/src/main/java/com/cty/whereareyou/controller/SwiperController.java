package com.cty.whereareyou.controller;

import com.cty.whereareyou.entity.swiper.FrontSwiper;
import com.cty.whereareyou.service.SwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/6 15:46
 */
@RestController
@CrossOrigin
@RequestMapping("/articles")
public class SwiperController {
    @Autowired
    private SwiperService swiperService;

    @GetMapping("/get")
    public List<FrontSwiper> getSwiperInfo(){
        return swiperService.getSwiperInfo();
    }
}
