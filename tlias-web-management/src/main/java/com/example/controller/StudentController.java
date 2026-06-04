package com.example.controller;

import com.example.pojo.PageResult;
import com.example.pojo.Result;
import com.example.pojo.Student;
import com.example.pojo.StudentQueryParam;
import com.example.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询学生数据");
        PageResult<Student> pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping("/students")
    public Result save(@RequestBody Student student) {
        log.info("保存学生数据，参数：{}", student);
        studentService.save(student);
        return Result.success();
    }

    @GetMapping("/students/{id}")
    public Result get(@PathVariable Integer id) {
        log.info("查询学生数据，参数：{}", id);
        Student student = studentService.get(id);
        return Result.success(student);
    }

    @PutMapping("/students")
    public Result update(@RequestBody Student student) {
        log.info("更新学生数据，参数：{}", student);
        studentService.update(student);
        return Result.success();
    }


    //    请求路径后面跟 ?参数名=value1,value2... 这样的用@RequestParam这个注解接收。
    //    请求路径后面跟 /value1,value2...这样的用@PathVariable这个注解接收简单记忆。
    //    问号（?）@RequestParam
    //    斜杠（/） @PathVariable
    //    可以到前端控制台查找到对应的日志


    @DeleteMapping("/students/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        log.info("删除学生数据，参数：{}", ids);
        studentService.delete(ids);
        return Result.success();
    }

    @PutMapping("/students/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id, @PathVariable Short score) {
        log.info("修改学生违规数据，参数：{} {}", id, score);
        studentService.updateViolation(id, score);
        return Result.success();
    }
}
