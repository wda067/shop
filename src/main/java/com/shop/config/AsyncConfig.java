package com.shop.config;

import com.shop.config.mdc.MdcTaskDecorator;
import com.shop.exception.CustomException;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-task-");

        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.initialize();
        return executor;
    }

    //@Override
    //public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    //    return (ex, method, params) -> {
    //        log.error("비동기 실행 중 예외 발생: 메서드={}, 예외={}, 코드={}, 메시지={}",
    //                method.getName(),
    //                ex.getClass().getSimpleName(),
    //                (ex instanceof CustomException) ? ((CustomException) ex).getErrorCode().getCode() : "N/A",
    //                (ex instanceof CustomException) ? ((CustomException) ex).getErrorCode().getMessage()
    //                        : ex.getMessage()
    //        );
    //    };
    //}
}
