package com.example.filter;

import com.alibaba.fastjson.JSON;
import com.example.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.example.common.R;

/**
 * 检查用户是否登录完成
 */
@Slf4j
@WebFilter("/*")
public class LoginCheckFilter implements Filter {

    //匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    //不需要登录就可以访问的接口
    public static final String[] URLS = {
            "/static/**",
            "/api/error/**",
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //白名单校验
        if (check(URLS, request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        // 登录校验
        if (request.getHeader("Token") != null) {
            filterChain.doFilter(request, response);
            return;
        }
        // 未登录,不让请求
        response.getWriter().write(JSON.toJSONString(R.error("用户未登录")));
    }

    public boolean check(String[] uris, String uri) {
        boolean r = false;
        for (String _uri : uris) {
            r = PATH_MATCHER.match(_uri, uri);
            if (r) {
                break;
            }
        }
        return r;
    }
}
