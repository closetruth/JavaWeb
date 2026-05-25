package com.example.mapper;


import com.example.pojo.ExpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    void insertBatch(List<ExpExpr> exprlist);

    void deleteByEmpIds(List<Integer> empIds);
}
