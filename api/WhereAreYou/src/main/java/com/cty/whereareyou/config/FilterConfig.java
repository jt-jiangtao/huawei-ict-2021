package com.cty.whereareyou.config;

import com.cty.whereareyou.filter.ReqsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jiangtao
 * @Date: 2021/11/4 22:25
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registrationReqsFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ReqsFilter());
        registration.setOrder(10);
        return registration;
    }
}
