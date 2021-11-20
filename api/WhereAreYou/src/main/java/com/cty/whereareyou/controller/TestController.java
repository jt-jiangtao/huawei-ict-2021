package com.cty.whereareyou.controller;

import com.cty.whereareyou.service.CompareFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: jiangtao
 * @Date: 2021/11/18 14:45
 */
@RestController
@CrossOrigin
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CompareFaceService compareFaceService;

    @PostMapping("/compare")
    public Object testCompareByUrl(@RequestParam("url1") String url1, @RequestParam("url2") String url2) {
        return compareFaceService.compareFaceByUrlSolution(url1, url2);
    }
}
