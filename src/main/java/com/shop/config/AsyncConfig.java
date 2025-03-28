package com.shop.config;

import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Bean
    public Executor emailTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-executor-");

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
