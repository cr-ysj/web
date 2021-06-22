package com.example.demo.config.pool;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * 线程池配置（@Async）
 * 实现AsyncConfigurer来配置线程池
 */
@Slf4j
@EnableAsync//通过@EnableAsync 注解启用@Async异步注解
@Configuration(proxyBeanMethods=true)//全量模式
public class SimpleExecutorConfig implements AsyncConfigurer {

    /** 线程池维护线程的核心数量 */
    @Value("${executor.corePoolSize}")
    private Integer corePoolSize;

    /** 线程池维护线程的最大数量 */
    @Value("${executor.maxPoolSize}")
    private Integer maxPoolSize;

    /** 缓冲队列的大小 */
    @Value("${executor.queueCapacity}")
    private Integer queueCapacity;

    /** 为每个线程名设置一个前缀（1） */
    @Value("${executor.threadNamePrefix}")
    private String threadNamePrefix;


    @Bean("CR_Thread")
    @Override
    public Executor getAsyncExecutor() {
        //线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setThreadNamePrefix(threadNamePrefix);
        taskExecutor.initialize();
        return taskExecutor;
    }



    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            log.info("Async method: {} has uncaught exception,params:{}", method.getName(), JSONUtil.toJsonStr(params));

            if (ex instanceof AsyncException) {
                AsyncException asyncException = (AsyncException) ex;
                log.info("asyncException:{}",asyncException.getErrorMessage());
            }

            log.info("Exception :");
            ex.printStackTrace();
        }
    }
    @Data
    class AsyncException extends Exception {
        private int code;
        private String errorMessage;
    }
}
