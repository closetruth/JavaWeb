package com.example.mapper;

import com.example.pojo.Student;
import com.example.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    List<Student> list(StudentQueryParam studentQueryParam);

    void insert(Student student);

    Student getById(Integer id);

    void updateById(Student student);

    void deleteByIds(List<Integer> ids);

    void updateViolation(Integer id, Short score);

    List<Map<String, Object>> countStudentCountData();

    List<Map<String, Object>> countStudentDegreeData();
}
