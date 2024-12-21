package com.thirdeye.purse.config;

import com.thirdeye.purse.interceptors.AuthorizationInterceptor;
import com.thirdeye.purse.utils.AllMicroservicesData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/api/holdedstock/**",
                "/api/marketthresold/**",
                "/api/userinfo/**",
                "/api/authmicroservices/**");
    }
}

