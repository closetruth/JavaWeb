package com.example.service;

import com.example.pojo.ClazzData;
import com.example.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();

    ClazzData getStudentCountData();

    List<Map<String, Object>> getStudentDegreeData();
}
