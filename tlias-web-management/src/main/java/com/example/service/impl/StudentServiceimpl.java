package com.example.service.impl;

import com.example.mapper.StudentMapper;
import com.example.pojo.PageResult;
import com.example.pojo.Student;
import com.example.pojo.StudentQueryParam;
import com.example.service.StudentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class StudentServiceimpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> page(StudentQueryParam studentQueryParam) {
        log.info("分页查询学生数据");
        Integer page = studentQueryParam.getPage() != null ? studentQueryParam.getPage() : 1;
        Integer pageSize = studentQueryParam.getPageSize() != null ? studentQueryParam.getPageSize() : 10;

        PageHelper.startPage(page, pageSize);
        // PageHelper 是一个 MyBatis 拦截器（Interceptor），配置了PageHelper.startPage后，它会在 MyBatis 执行 SQL 之前"劫持"并修改 SQL。

        List<Student> studentList = studentMapper.list(studentQueryParam);

        Page<Student> p = (Page<Student>) studentList;

        return new PageResult<Student>(p.getTotal(), p.getResult());
    }

    @Override
    public void save(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        if (student.getViolationCount() == null) {
            student.setViolationCount((short) 0);
        }
        if (student.getViolationScore() == null) {
            student.setViolationScore((short) 0);
        }
        studentMapper.insert(student);
    }

    @Override
    public Student get(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateById(student);
    }

    @Override
    public void delete(List<Integer> ids) {
         studentMapper.deleteByIds(ids);
    }

    @Override
    public void updateViolation(Integer id, Short score) {
        studentMapper.updateViolation(id, score);
    }
}
