package com.example.app;



import com.example.anno.Log;
import com.example.mapper.OperateLogMapper;
import com.example.pojo.OperateLog;
import com.example.utils.CurrentHolder;
import com.example.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志切面类
 * 用于记录系统增、删、改功能接口的操作日志
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final OperateLogMapper operateLogMapper;
    private final ObjectMapper objectMapper;

    /**
     * 环绕通知：拦截所有使用@Log注解的方法，记录操作日志
     */
    @Around("@annotation(com.example.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 2. 获取操作人ls
        
        Integer operateEmpId = CurrentHolder.getCurrentId();

        // Integer operateEmpId = getOperateEmpId(request);

        // 3. 获取目标类和方法信息
        String className = joinPoint.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();

        // 4. 获取方法参数
        String[] paramNames = signature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        String methodParams = getMethodParams(paramNames, paramValues);

        // 5. 记录方法执行开始时间
        long startTime = System.currentTimeMillis();

        // 6. 执行目标方法
        Object result = joinPoint.proceed();

        // 7. 计算方法执行耗时
        long costTime = System.currentTimeMillis() - startTime;

        // 8. 获取返回值
        String returnValue = getReturnValue(result);

        // 9. 构建操作日志对象
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(operateEmpId);
        operateLog.setOperateTime(LocalDateTime.now());
        operateLog.setClassName(className);
        operateLog.setMethodName(methodName);
        operateLog.setMethodParams(methodParams);
        operateLog.setReturnValue(returnValue);
        operateLog.setCostTime(costTime);

        // 10. 保存日志到数据库
        try {
            operateLogMapper.insert(operateLog);
            log.info("操作日志记录成功：{}", operateLog);
        } catch (Exception e) {
            log.error("操作日志记录失败", e);
        }

        return result;
    }

    /**
     * 从JWT token中解析操作人ID
     */
    private Integer getOperateEmpId(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token != null && !token.isEmpty()) {
            try {
                Map<String, Object> claims = JwtUtils.parseToken(token);
                return (Integer) claims.get("id");
            } catch (Exception e) {
                log.warn("解析JWT token失败", e);
            }
        }
        return null; // 如果无法获取操作人ID，返回null
    }

    /**
     * 将方法参数转换为JSON字符串
     */
    private String getMethodParams(String[] paramNames, Object[] paramValues) {
        if (paramNames == null || paramNames.length == 0) {
            return "{}";
        }
        try {
            Map<String, Object> params = new HashMap<>();
            for (int i = 0; i < paramNames.length; i++) {
                params.put(paramNames[i], paramValues[i]);
            }
            return objectMapper.writeValueAsString(params);
        } catch (Exception e) {
            log.warn("序列化方法参数失败", e);
            return "{}";
        }
    }

    /**
     * 将返回值转换为JSON字符串
     */
    private String getReturnValue(Object result) {
        if (result == null) {
            return null;
        }
        try {
            String json = objectMapper.writeValueAsString(result);
            // 限制返回值长度，避免超过数据库字段限制
            if (json.length() > 2000) {
                return json.substring(0, 1997) + "...";
            }
            return json;
        } catch (Exception e) {
            log.warn("序列化返回值失败", e);
            return result.toString();
        }
    }
}
