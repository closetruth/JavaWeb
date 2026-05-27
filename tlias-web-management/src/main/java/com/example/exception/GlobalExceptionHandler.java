package com.example.exception;

import com.example.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    public Result handleException(Exception e) {
        log.error("服务器发生异常：{}", e);
        e.printStackTrace();
        return Result.error("服务器发生异常");
    }

    public Result handleDulplicateKeyException(DuplicateKeyException e) {
        log.error("服务器发生异常：{}", e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.error("【" + arr[2] + "】已存在");
    }
}
