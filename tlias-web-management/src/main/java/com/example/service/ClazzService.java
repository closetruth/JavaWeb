package com.example.service;

import com.example.pojo.Clazz;
import com.example.pojo.ClazzQueryParam;
import com.example.pojo.PageResult;

import java.util.List;

public interface ClazzService {

    PageResult<Clazz> page(ClazzQueryParam clazzQueryParam);

    void delete(Integer id);

    void save(Clazz clazz);

    Clazz get(Integer id);

    void update(Clazz clazz);

    List<Clazz> listAllClazzs();
}
