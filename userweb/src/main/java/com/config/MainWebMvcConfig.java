package com.config;


import com.intercept.AuthIntercept;
import com.intercept.ReplayAttackIntercept;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ank
 * @version v 1.0
 * @title [标题]
 * @ClassName: com.spinfosec.config.MainWebMvcConfig
 * @description [一句话描述]
 * @create 2018/10/9 14:17
 * @copyright Copyright(C) 2018 SHIPING INFO Corporation. All rights reserved.
 */
@Configuration
public class MainWebMvcConfig implements WebMvcConfigurer
{

    @Bean
    public HandlerInterceptor getReplayAttackIntercept()
    {
        return new ReplayAttackIntercept();
    }

    @Bean
    public HandlerInterceptor getAuthIntercept()
    {
        return new AuthIntercept();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(getReplayAttackIntercept()).addPathPatterns("/**")
                .excludePathPatterns("/sysapi/login/**")
                .excludePathPatterns("/sysapi/lwaddr/getThreadInfo")
                .excludePathPatterns("/CloudApi/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");;// 前端通过此isShowAuthCode接口来同步服务器时间戳，故此接口不列入重放攻击序列
//        registry.addInterceptor(getAuthIntercept()).addPathPatterns("/**")
//                .excludePathPatterns("/sysapi/login/**")
//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");;
        registry.addInterceptor(getAuthIntercept()).addPathPatterns("/**");;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
