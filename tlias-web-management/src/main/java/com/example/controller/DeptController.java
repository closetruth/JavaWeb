package com.example.controller;

import com.example.anno.Log;
import com.example.pojo.Dept;
import com.example.pojo.Result;
import com.example.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/depts")
    public Result list() {
//        System.out.println("查询全部部门数据");
        log.info("查询全部部门数据");
        List<Dept> depList = deptService.findAll();
        return Result.success(depList);
    }

    @Log
    @DeleteMapping("/depts")
    public Result delete(Integer id) {
//        System.out.println("删除部门数据，id：" + id);
        if (deptService.deleteById(id)) {
            log.info("删除部门数据，id：" + id);
            return Result.success();
        } else {
            log.error("删除部门数据，id：" + id + " 失败 该部门下有员工");
            return Result.error("删除失败 该部门下有员工");
        }
    }

    @Log
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept) {
//        System.out.println("添加部门数据：" + dept);
        log.info("添加部门数据：" + dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/depts/{id}")
    public Result get(@PathVariable Integer id) {
//        System.out.println("查询部门数据，id：" + id);
        log.info("查询部门数据，id：" + id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @Log
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept) {
//        System.out.println("修改部门数据：" + dept);
        log.info("修改部门数据：" + dept);
        deptService.update(dept);
        return Result.success();
    }
}
