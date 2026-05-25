package com.example.controller;

import com.example.pojo.Emp;
import com.example.pojo.EmpQueryParam;
import com.example.pojo.PageResult;
import com.example.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import com.example.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 分页查询员工数据
     * @return
     */

//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer pageSize,
//                       String name, Integer gender,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
//        log.info("分页查询员工数据，参数：{},{},{},{},{},{}", page, pageSize, name, gender, begin, end);
//        PageResult<Emp> pageResult = empService.page(page, pageSize, name, gender,  begin, end);
//        return Result.success(pageResult);
//    }


    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("分页查询员工数据，参数：{}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("新增员工数据，参数：{}", emp);
        empService.save(emp);
        return Result.success();
    }

//    @DeleteMapping
//    public Result delete(Integer[] ids) {
//        log.info("删除员工数据，参数：{}", Arrays.toString(ids));
//        return Result.success();
//    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除员工数据，参数：{}", ids);
        empService.delete(ids);
        return Result.success();
    }

    @GetMapping("{id}")
    public Result get(@PathVariable Integer id) {
        log.info("查询员工数据，参数：{}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

}
