package com.example.service;

import com.example.pojo.OperateLog;
import com.example.pojo.OperateLogQueryParam;
import com.example.pojo.PageResult;
import org.springframework.stereotype.Service;

// @Service 错点，把这个注解放到Service接口里面没有放到Service实现类里面！！！
public interface OperateLogService {
    PageResult<OperateLog> page(OperateLogQueryParam operateLogQueryParam);
}
