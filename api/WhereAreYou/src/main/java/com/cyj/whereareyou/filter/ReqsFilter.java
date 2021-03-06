package com.cyj.whereareyou.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: jiangtao
 * @Date: 2021/11/4 21:54
 */
@WebFilter(urlPatterns = {"/api/*"}, filterName = "ReqsFilter")
public class ReqsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterHead(servletRequest, servletResponse);
        servletRequest.getParameter("token");
        filterChain.doFilter(servletRequest, servletResponse);
        filterTail(servletRequest, servletResponse);
    }

    private void filterHead(ServletRequest servletRequest, ServletResponse servletResponse) {
        System.out.println(servletRequest);
    }

    private void filterTail(ServletRequest servletRequest, ServletResponse servletResponse) {

    }

}
