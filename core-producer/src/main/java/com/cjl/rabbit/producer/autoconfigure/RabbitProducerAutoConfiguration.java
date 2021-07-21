package com.cjl.rabbit.producer.autoconfigure;

import com.cjl.rabbit.task.annotation.EnableElasticJob;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * $RabbitProducerAutoConfiguration 自动装配
 * @Author: Be_Young
 * @Date: 2021/4/28 13:48
 */
@EnableElasticJob
@Configuration
@ComponentScan("com.cjl.rabbit.producer.*")
public class RabbitProducerAutoConfiguration {



}
