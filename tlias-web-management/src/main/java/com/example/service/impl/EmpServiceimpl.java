package com.example.service.impl;

import com.example.mapper.EmpExprMapper;
import com.example.mapper.EmpMapper;
import com.example.pojo.*;
import com.example.service.EmpLogService;
import com.example.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class EmpServiceimpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    EmpLogService empLogService;
    /*
    原始查询
     */
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//        Long total = empMapper.count();
//        Integer start = (page - 1) * pageSize;
//        List<Emp> list = empMapper.page(start, pageSize);
//        return new PageResult<Emp>(total, list);
//    }

//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
//        PageHelper.startPage(page, pageSize);
//
//        List<Emp> emplist = empMapper.list(name, gender, begin, end);
//
//        Page<Emp> p = (Page<Emp>) emplist;
//
//        return new PageResult<Emp>(p.getTotal(), p.getResult());
//    }

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 添加 null 判断，设置默认值
        Integer page = empQueryParam.getPage() != null ? empQueryParam.getPage() : 1;
        Integer pageSize = empQueryParam.getPageSize() != null ? empQueryParam.getPageSize() : 10;
        PageHelper.startPage(page, pageSize);

//        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());


        List<Emp> emplist = empMapper.list(empQueryParam);

        Page<Emp> p = (Page<Emp>) emplist;

        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(Emp emp) {
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

            List<ExpExpr> exprlist = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprlist)){
                exprlist.forEach(expr -> expr.setEmpId(emp.getId()));
                empExprMapper.insertBatch(exprlist);
            }
        } finally {
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工：" + emp);
            empLogService.insertLog(empLog);

        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
        empMapper.deleteByIds(ids);
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        List<ExpExpr> exprlist = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprlist)){
            exprlist.forEach(expr -> expr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprlist);
        }
    }
}
