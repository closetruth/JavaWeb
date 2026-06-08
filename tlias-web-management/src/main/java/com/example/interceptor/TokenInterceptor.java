package com.example.interceptor;

import com.example.utils.CurrentHolder;
import com.example.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


//        String requestURI = request.getRequestURI();
//        if (requestURI.contains("/login")) {
//            log.info("登录请求，放行");
//            return true;
//        }

        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("token为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            // 难点之前把下面的代码放到过滤器里面了，但是过滤器是没有启用的,debug时一直没有debug出，直接跳过了代码，因为代码没有运行。
            Claims claims =JwtUtils.parseToken(token);
            Integer id = (Integer) claims.get("id");
            CurrentHolder.setCurrentId(id);
            log.info("当前用户id为：{}",id);
        } catch (Exception e) {
            log.info("令牌非法，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        log.info( "token合法");
        return true;
    }
}
