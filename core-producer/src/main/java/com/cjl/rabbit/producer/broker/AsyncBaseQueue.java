package com.cjl.rabbit.producer.broker;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 用于生成端异步发送消息
 * @Author: Be_Young
 * @Date: 2021/4/28 15:10
 */
@Slf4j
public class AsyncBaseQueue {

    private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int QUEUE_SIZE = 10000;

    /**
     * 异步发送消息
     */
    private static ExecutorService senderAsync = new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE * 2,
            60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(QUEUE_SIZE), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("RabbitMQ_client_async_sender");
            return thread;
        }
    }, new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.error("async sender is error rejected, Runnable: {}, Executor: {}", r, executor);
        }
    });

    public static void submit(Runnable runnable){
        senderAsync.submit(runnable);
    }

}
