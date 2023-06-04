package com.hulon.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.hulon.reggie.common.BaseContext;
import com.hulon.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 检查用户是否完成登录
 * @author Hulon
 * @date 2023/5/15
 * @className LoginCheckFilter
 */
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器,支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1 获取本次请求的URI
        String requestURI = request.getRequestURI();

        //不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
        //2 判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //3 如果不需要处理,则直接放行
        if (check){
            //log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //4-1 判断登录状态,如果已登录,则直接放行
        if (request.getSession().getAttribute("employee") != null){
            //log.info("用户已登录:id为{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);


            filterChain.doFilter(request,response);
            return;
        }
        //4-2 移动端 判断登录状态,如果已登录,则直接放行
        if (request.getSession().getAttribute("user") != null){
            //log.info("用户已登录:id为{}",request.getSession().getAttribute("employee"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);


            filterChain.doFilter(request,response);
            return;
        }
        //5 如果未登录则返回未登录结果,通过输出流的方式向客户端响应数据
        //log.info("没有登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    public boolean check(String[] urls,
                         String requestURL){
        for (String url :
                urls) {
            boolean match = PATH_MATCHER.match(url,requestURL);
            if (match){
                return true;
            }
        }
        return false;
    }
}
