package com.example.service.impl;

import com.example.mapper.EmpMapper;
import com.example.mapper.StudentMapper;
import com.example.pojo.ClazzData;
import com.example.pojo.JobOption;
import com.example.pojo.Student;
import com.example.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class ReportServiceimpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String, Object>> list = empMapper.countEmpJobData();

        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();
//        System.out.println(jobList);
//        System.out.println(dataList);
//        System.out.println(new JobOption(jobList, dataList));

        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        List<Map<String, Object>> empGenderList = empMapper.countEmpGenderData();
        return empGenderList;
    }

    @Override
    public ClazzData getStudentCountData() {
        List<Map<String, Object>> list = studentMapper.countStudentCountData();
        List<Object> clazzList = list.stream().map(dataMap -> dataMap.get("clazz")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();

//        System.out.println(clazzList);
//        System.out.println(dataList);
//        System.out.println(new ClazzData(clazzList, dataList));

        return new ClazzData(clazzList, dataList);
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        List<Map<String, Object>> studentDegreelist = studentMapper.countStudentDegreeData();

        log.info("统计学生学历数:{}", studentDegreelist);
        return studentDegreelist;
    }
}
