package com.example.service;

import com.example.pojo.Emp;
import com.example.pojo.EmpQueryParam;
import com.example.pojo.PageResult;

import java.time.LocalDate;

public interface EmpService {
//    PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end);

    PageResult<Emp> page(EmpQueryParam empQueryParam);

    void save(Emp emp);
}
