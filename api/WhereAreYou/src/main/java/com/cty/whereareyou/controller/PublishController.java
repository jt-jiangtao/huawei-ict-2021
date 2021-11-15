package com.cty.whereareyou.controller;

import com.cty.whereareyou.core.UnifyResponse;
import com.cty.whereareyou.entity.publish.LossSimpleInfo;
import com.cty.whereareyou.service.PublishService;
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
}
