package com.cyj.whereareyou.utils;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: jiangtao
 * @Date: 2021/12/4 20:23
 */
public class Request {

    static public Object requestPictureGenerate(String imageUrl, String userId, int lossId){
        String url = "http://123.60.110.121:8080/";
        RestTemplate restTemplate = new RestTemplate();
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("url", imageUrl);
        body.add("user_id", userId);
        body.add("loss_id",lossId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity httpEntity = new HttpEntity(body,headers);
        try {
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
            return strbody.getBody();

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
