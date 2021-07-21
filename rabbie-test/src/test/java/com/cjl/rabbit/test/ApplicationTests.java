package com.cjl.rabbit.test;

import com.cjl.rabbit.api.Message;
import com.cjl.rabbit.api.MessageType;
import com.cjl.rabbit.producer.broker.ProducerClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Be_Young
 * @Date: 2021/5/2 19:54
 */
@SpringBootTest
@Slf4j
public class ApplicationTests {

    @Autowired
    private ProducerClient producerClient;

    @Test
    public void testProducerClient() throws Exception{
        for (int i = 0; i < 1; ++i){
            String uniqueId = UUID.randomUUID().toString();
            Map<String,Object> attributes = new HashMap<>();
            attributes.put("name","张三");
            attributes.put("age","18");
            Message message = new Message(uniqueId,"exchange-1","spring.abc",attributes,MessageType.RELIANT);
            producerClient.send(message);
        }
        Thread.sleep(100000);
    }

    @Test
    public void testProducerClient2() throws Exception{
        for (int i = 0; i < 1; ++i){
            String uniqueId = UUID.randomUUID().toString();
            Map<String,Object> attributes = new HashMap<>();
            attributes.put("name","张三");
            attributes.put("age","18");
            Message message = new Message(uniqueId,"delay-exchange","delay.abc",attributes,MessageType.RELIANT,
                    10000);
            producerClient.send(message);
        }
        log.warn("alreadySend");
        Thread.sleep(5000);
        log.warn("过去5秒");
        Thread.sleep(5000);
        log.warn("过去10秒");
        Thread.sleep(100000);
    }

}
