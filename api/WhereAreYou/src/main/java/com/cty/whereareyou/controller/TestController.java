package com.cty.whereareyou.controller;

import com.cty.whereareyou.service.CompareFaceService;
import com.cty.whereareyou.service.ImageService;
import com.cty.whereareyou.service.SearchService;
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

    @Autowired
    private ImageService imageService;

    @Autowired
    private SearchService searchService;

    @PostMapping("/compare")
    public Object testCompareByUrl(@RequestParam("url1") String url1, @RequestParam("url2") String url2) {
        return compareFaceService.compareFaceByUrlSolution(url1, url2);
    }

    @GetMapping("/data1")
    public  Object testHMGetData1(@RequestParam("url1") String url1){
        return url1;
    }

    @PostMapping("/data2")
    public  Object testHMGetData2(@RequestParam("url1") String url1){
        return url1;
    }

    @PostMapping("/m1")
    public Object m1(@RequestParam("user") int user, @RequestParam("image") String image,@RequestParam("loss") int loss){
        return imageService.insertRawImage(user, image, loss);
    }

    @PostMapping("/m2")
    public Object m2(@RequestParam("user") int user, @RequestParam("image") String image,@RequestParam("loss") int loss){
        return imageService.insertGenerateImage(user, image, loss);
    }

    @PostMapping("/s")
    public void search(){
        searchService.findParent("https://image.jiangtao.website/t/2.png", 10);
    }
}
