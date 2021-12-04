package com.cyj.whereareyou.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyj.whereareyou.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private UserService userService;

    @Autowired
    private PublishService publishService;

    @Autowired
    private ClewService clewService;

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

    @PostMapping("/u")
    public Object insertUser(){
        return userService.insertUser("token", "name", "url", "openid");
    }

    @PostMapping("/i")
    public Object insertFindInfo(){
//        return publishService.commitFindChild(12, "2021-12-3 17:00", "location", 1, "name", "男", "detail characters", "case detail", UsernameUtils.transformToId("user@10000093"), "['url363636']", "[{'name': 'n','phone': '18337','location':'ll','relation':'re'}]");
        JSONObject jsonObjectC = JSON.parseObject("{}");
        jsonObjectC.put("content", "查找成功！！人脸对比相似率为" + 0.93 + ", 请练习对方确认。同时，系统将向双方提供联系方式。祝愿早日找到父母！！！");
        JSONArray contacts = new JSONArray();
        publishService.selectContacts(1).forEach(contact -> {
            JSONObject jb = JSON.parseObject("{}");
            jb.put("id", contact.getId());
            jb.put("name", contact.getName());
            jb.put("phone", contact.getPhone());
            jb.put("location", contact.getLocation());
            jb.put("relation", contact.getRelation());
            contacts.add(jb);
        });
        JSONArray images = new JSONArray();
        images.add("https://image.jiangtao.website/generate/0_1_g_4.png");
        jsonObjectC.put("contacts", contacts);
        jsonObjectC.put("images", images);
        clewService.insertNotification(-1, 13, jsonObjectC.toJSONString(), "FIND");
        return jsonObjectC;
    }

    @GetMapping("/r")
    public Object request() {
        String url = "http://123.60.110.121:8080/";
        RestTemplate restTemplate = new RestTemplate();
        LinkedMultiValueMap body=new LinkedMultiValueMap();
        body.add("url","https://image.jiangtao.website/raw/0_2_raw_1.jpg");
        body.add("user_id","user@10000013");
        body.add("loss_id",19);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity httpEntity = new HttpEntity(body,headers);
        try {
            //使用 exchange 发送请求，以String的类型接收返回的数据
            //ps，我请求的数据，其返回是一个json
            ResponseEntity<String> strbody = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
            //解析返回的数据
            return strbody.getBody();

        }catch (Exception e){
            System.out.println(e);
        }
        return  null;
    }
}
