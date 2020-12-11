package cn._51even.wireless.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    // 核心线程数
    private static final int CORE_POOL_SIZE = 4;
    // 最大线程数
    private static final int MAX_POOL_SIZE = 8;
    // 队列容量
    private static final int QUEUE_CAPACITY = 20;
    // 线程活跃时间（秒）
    private static final int KEEP_ALIVE_SECONDS = 60;
    // 线程名称前缀
    private static final String THREAD_NAME_PREFIX = "threadPool-";
    // 拒绝策略（当pool已经达到max size的时候，不在新线程中执行任务，而是有调用者所在的线程来执行）
    private static final RejectedExecutionHandler THREAD_POOL_EXECUTOR = new ThreadPoolExecutor.CallerRunsPolicy();

    @Bean("executorService")
    public ExecutorService executorService() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setRejectedExecutionHandler(THREAD_POOL_EXECUTOR);
        executor.initialize();
        ThreadPoolExecutor threadPoolExecutor = executor.getThreadPoolExecutor();
        return threadPoolExecutor;
    }
}
