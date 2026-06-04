package com.example.service.impl;

import com.example.mapper.DeptMapper;
import com.example.mapper.EmpMapper;
import com.example.pojo.Dept;
import com.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceimpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        if (empMapper.idHasEmp(id)) {
            return false;
        } else {
            deptMapper.deleteById(id);
            return true;
        }
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        Dept dept = deptMapper.getById(id);
        return dept;
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }
}
