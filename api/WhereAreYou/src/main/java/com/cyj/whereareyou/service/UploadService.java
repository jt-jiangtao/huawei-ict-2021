package com.cyj.whereareyou.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author: jiangtao
 * @Date: 2021/11/22 14:02
 */
public interface UploadService {

    Object uploadOneFile(MultipartFile file);

    Map<String, String> uploadGenerateFile(MultipartFile file);
}
