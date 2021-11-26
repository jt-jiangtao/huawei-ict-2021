package com.cty.whereareyou.controller;

import com.cty.whereareyou.service.ImageService;
import com.cty.whereareyou.service.SearchService;
import com.cty.whereareyou.service.UploadService;
import com.cty.whereareyou.utils.UsernameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 13:34
 */
@RestController
@CrossOrigin
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SearchService searchService;


    @PostMapping("/raw")
    public Object uploadRaw(@RequestParam("file") MultipartFile file) {
        return uploadService.uploadOneFile(file);
    }

    @PostMapping("/generate")
    public Object uploadGenerate(@RequestParam("file") MultipartFile file, @RequestParam("user_id") String userId, @RequestParam("loss_id") int lossId) {
        Map<String, String> map = uploadService.uploadGenerateFile(file);
        boolean flag = false;
        if (map.get("status").equals("200")) {
            int status = ((Map<String, Integer>)imageService.insertGenerateImage(UsernameUtils.transformToId(userId), map.get("url"), lossId)).get("status");
            if (status >= 1) {
                flag = true;
                searchService.findChild(map.get("url"), lossId);
            }
        }
        return flag ? "search...." : "error";
    }

}
