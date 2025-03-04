package com.shop.config;

import com.shop.config.auth.AuthArgumentResolver;
import com.shop.config.auth.AuthInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/v*/**")
                .excludePathPatterns(
                        "/api/v*/join",
                        "/api/v*/login",
                        "/api/v*/logout",
                        "/api/v*/members/**",
                        "/api/v*/product/**",
                        //"/api/v*/payment/**",
                        "/api/*.ico",
                        "/api/error");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver());
    }
}
