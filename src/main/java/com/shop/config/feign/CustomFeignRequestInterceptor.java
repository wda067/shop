package com.shop.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class CustomFeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("Content-Type", "application/json");
        String requestId = MDC.get("requestId");
        if (requestId != null) {
            template.header("X-Request-Id", requestId);
        }
        log.info("[Feign RequestInterceptor] Request Method: {}, URL: {}", template.method(), template.url());
    }
}

