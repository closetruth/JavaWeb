package com.example.controller;

import com.example.pojo.Clazz;
import com.example.pojo.ClazzQueryParam;
import com.example.pojo.PageResult;
import com.example.pojo.Result;
import com.example.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @GetMapping("/clazzs")
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("分页查询班级数据");
        PageResult<Clazz> pageResult= clazzService.page(clazzQueryParam);
        return Result.success(pageResult);
    }

    @DeleteMapping("/clazzs/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除班级数据，参数：{}", id);
        clazzService.delete(id);
        return Result.success();
    }

    @PostMapping("/clazzs")
    public Result save(@RequestBody Clazz clazz) {
        log.info("保存班级数据，参数：{}", clazz);
        clazzService.save(clazz);
        return Result.success();
    }

    @GetMapping("/clazzs/{id}")
    public Result get(@PathVariable Integer id) {
        log.info("查询班级数据，参数：{}", id);
        Clazz clazz = clazzService.get(id);
        return Result.success(clazz);
    }

    @PutMapping("/clazzs")
    public Result update(@RequestBody Clazz clazz) {
        log.info("更新班级数据，参数：{}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    @GetMapping("/clazzs/list")
    public Result listAllClazzs() {
        log.info("查询所有班级数据");
        List<Clazz> clazzList = clazzService.listAllClazzs();
        return Result.success(clazzList);
    }

}
