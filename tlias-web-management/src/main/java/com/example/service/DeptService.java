package com.example.service;

import com.example.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 查询全部部门数据
     * @return
     */
    List<Dept> findAll();

    /**
     * 删除部门数据
     * @param id
     */
    void deleteById(Integer id);

    void add(Dept dept);

    Dept getById(Integer id);

    void update(Dept dept);
}
