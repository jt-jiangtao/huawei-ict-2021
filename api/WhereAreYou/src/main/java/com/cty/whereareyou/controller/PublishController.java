package com.cty.whereareyou.controller;

import com.cty.whereareyou.core.UnifyResponse;
import com.cty.whereareyou.entity.publish.LossSimpleInfo;
import com.cty.whereareyou.service.PublishService;
import com.cty.whereareyou.utils.UsernameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: jiangtao
 * @Date: 2021/11/14 23:05
 */
@RestController
@CrossOrigin
@RequestMapping("/loss")
public class PublishController {
    @Autowired
    private PublishService publishService;

    @GetMapping("/user/{id}")
    public Object getSimpleInfo(@PathVariable("id") Integer id) {
        return publishService.selectUserSimpleInfo(id);
    }

    @GetMapping("/simple_info")
    public Object getLossSimpleInfo(){
        return publishService.selectAllSimpleInfo();
    }

    // 关键字检索
    @GetMapping("/key")
    public Object getLossSimpleInfoByKey(String key){
        return publishService.selectByKey(key);
    }

    @GetMapping("/detail/{id}")
    public Object getLossDetailInfo(@PathVariable("id") Integer id){
        return publishService.selectDetailLossById(id);
    }

    @GetMapping("/simple/child/{id}")
    public Object getAllSimpleInfoByUserOfFindChild(@PathVariable("id") Integer id){
        return publishService.selectAllSimpleInfoByUserOfFindChild(id);
    }

    @GetMapping("/simple/parent/{id}")
    public Object getAllSimpleInfoByUserOfFindParent(@PathVariable("id") Integer id){
        return publishService.selectAllSimpleInfoByUserOfFindParent(id);
    }

    @PostMapping("/move/trash")
    public UnifyResponse moveToTrash(@RequestParam(name = "id") int id){
        int code = publishService.moveToTrash(id);
        if (code == 1) return new UnifyResponse(12101);
        return new UnifyResponse(12202);
    }

    @PostMapping("/move/delete")
    public UnifyResponse deleteFromTrash(@RequestParam(name = "id") int id){
        int code = publishService.deleteFromTrash(id);
        if (code == 1) return new UnifyResponse(12101);
        return new UnifyResponse(12202);
    }

    @PostMapping("/move/back")
    public UnifyResponse moveFromTrashToNormalORBack(@RequestParam(name = "id") int id){
        int code = publishService.moveFromTrashToNormalORBack(id);
        if (code == 1) return new UnifyResponse(12105);
        return new UnifyResponse(12206);
    }

    @GetMapping("/collect/add")
    public Object collectAdd(String id, String event){
        return publishService.collectAdd(id, event);
    }

    @GetMapping("/collect/remove")
    public Object collectRemove(String id, String event){
        return publishService.collectRemove(id, event);
    }

    @GetMapping("/is_collect")
    public Object isCollect(String id, String event){
        return publishService.isCollect(id, event);
    }

    @GetMapping("/collect/number")
    public Object collectNumber(String id){
        return publishService.collectNumber(id);
    }

    @GetMapping("/collect/simple_info")
    public Object collectSimpleInfoByUser(String id){
        return publishService.collectSimpleInfoByUser(id);
    }

    @PostMapping("/add/contact")
    public Object addContact(String name, String phone, String location, String userId, String relation) {
        return publishService.addContact(name, phone, location, Integer.parseInt(userId.split("@")[1]), relation);
    }

    @PostMapping("/update/contact")
    public Object updateContact(String id, String name, String phone, String location, String userId, String relation) {
        return publishService.updateContact(id, name, phone, location, userId, relation);
    }

    @PostMapping("/remove/contact")
    public Object removeContact(int id, int userId){
        return publishService.removeContact(id, userId);
    }

    @GetMapping("/get/contact")
    public Object getContact(@RequestParam("id") String userId){
        return publishService.selectContacts(UsernameUtils.transformToId(userId));
    }

    @PostMapping("/commit/child")
    public Object commitFindChild(int age, String lossTime, String lossLocation, int reportPolice, String name, String sex, String detailCharacters, String caseDetail, int userId, String images, String contacts) {
        return publishService.commitFindChild(age, lossTime, lossLocation, reportPolice, name, sex, detailCharacters, caseDetail, userId, images, contacts);
    }

    @PostMapping("/commit/parent")
    public Object commitParent(int age, String lossTime, String lossLocation, int reportPolice, String name, String sex, String detailCharacters, String caseDetail, int userId, String images, String contacts) {
        return publishService.commitParent(age, lossTime, lossLocation, reportPolice, name, sex, detailCharacters, caseDetail, userId, images, contacts);
    }
}
