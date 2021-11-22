package com.cty.whereareyou.service.Impl;

import com.cty.whereareyou.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 14:02
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Override
    public Object uploadOneFile(MultipartFile file) {
        Map<String, String> map = new HashMap<>();
        if (file.isEmpty()) {
            map.put("status", "000");
            return map;
        }

        UUID randomUUID = UUID.randomUUID();
        String originalName = file.getOriginalFilename();
        String fileName = randomUUID.toString() + originalName.substring(originalName.toString().lastIndexOf("."));
        String filePath = "/www/wwwroot/image.jiangtao.website/raw";
        File dest = new File(filePath ,fileName);
        try {
            file.transferTo(dest);
            map.put("status", "200");
            map.put("url", "https://image.jiangtao.website/raw/" + fileName);
        } catch (IOException e) {
            map.put("status", "400");
            log.error(e.toString(), e);
        }
        return map;
    }

    @Override
    public Map<String, String> uploadGenerateFile(MultipartFile file) {
        Map<String, String> map = new HashMap<>();
        if (file.isEmpty()) {
            map.put("status", "000");
            return map;
        }

        UUID randomUUID = UUID.randomUUID();
        String originalName = file.getOriginalFilename();
        String fileName = randomUUID.toString() + originalName.substring(originalName.toString().lastIndexOf("."));
//        String filePath = "E:\\upload";
        String filePath = "/www/wwwroot/image.jiangtao.website/generate";
        File dest = new File(filePath ,fileName);
        try {
            file.transferTo(dest);
            map.put("status", "200");
            map.put("url", "https://image.jiangtao.website/generate/" + fileName);
        } catch (IOException e) {
            map.put("status", "400");
            log.error(e.toString(), e);
        }
        return map;
    }

}
