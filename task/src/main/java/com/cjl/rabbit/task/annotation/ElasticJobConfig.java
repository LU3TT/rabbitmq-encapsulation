package com.cjl.rabbit.task.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Be_Young
 * @Date: 2021/5/1 17:24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticJobConfig {

    String name();	//elasticjob的名称

    // cron表达式
    String cron() default "";

    //分片总数
    int shardingTotalCount() default 2;

    //
    String shardingItemParameters() default "";

    String jobParameter() default "";

    boolean failover() default false;

    boolean misfire() default true;

    String description() default "";

    boolean overwrite() default false;

    boolean streamingProcess() default false;

    String scriptCommandLine() default "";

    boolean monitorExecution() default false;

    public int monitorPort() default -1;	//must

    public int maxTimeDiffSeconds() default -1;	//must

    public String jobShardingStrategyClass() default "";	//must

    public int reconcileIntervalMinutes() default 10;	//must

    public String eventTraceRdbDataSource() default "";	//must

    public String listener() default "";	//must

    public boolean disabled() default false;	//must

    public String distributedListener() default "";

    public long startedTimeoutMilliseconds() default Long.MAX_VALUE;	//must

    public long completedTimeoutMilliseconds() default Long.MAX_VALUE;		//must

    public String jobExceptionHandler() default "com.dangdang.ddframe.job.executor.handler.impl.DefaultJobExceptionHandler";

    public String executorServiceHandler() default "com.dangdang.ddframe.job.executor.handler.impl.DefaultExecutorServiceHandler";

}