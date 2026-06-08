package com.example.filter;

import com.example.utils.CurrentHolder;
import com.example.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;

import java.io.IOException;
import java.net.http.HttpRequest;

//@WebFilter("/*")
@Slf4j
public class TokenFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("token为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Claims claims =JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("令牌非法，响应401");
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        log.info( "token合法");
        chain.doFilter(request, response);

        CurrentHolder.remove();
    }
}
