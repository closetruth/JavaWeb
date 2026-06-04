package com.example.service;

import com.example.pojo.PageResult;
import com.example.pojo.Student;
import com.example.pojo.StudentQueryParam;

import java.util.List;

public interface StudentService {
    PageResult<Student> page(StudentQueryParam studentQueryParam);

    void save(Student student);

    Student get(Integer id);

    void update(Student student);

    void delete(List<Integer> ids);

    void updateViolation(Integer id, Short score);
}
