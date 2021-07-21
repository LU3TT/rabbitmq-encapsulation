package com.cjl.rabbit.producer.broker;

import com.cjl.rabbit.api.Message;
import com.cjl.rabbit.api.MessageType;
import com.cjl.rabbit.api.exception.MessageException;
import com.cjl.rabbit.api.exception.MessageRunTimeException;
import com.cjl.rabbit.common.converter.GenericMessageConverter;
import com.cjl.rabbit.common.converter.RabbitMessageConverter;
import com.cjl.rabbit.common.serializer.Serializer;
import com.cjl.rabbit.common.serializer.SerializerFactory;
import com.cjl.rabbit.common.serializer.impl.JacksonSerializerFactory;
import com.cjl.rabbit.producer.service.MessageStoreService;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: Be_Young
 * @Date: 2021/4/28 16:28
 */
@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback{

    /**
     * 对rabbitmq连接进行池化处理：对不同的Topic(也就是对应不同Exchange由不同的Template执行任务)
     */
    private Map<String, RabbitTemplate> rabbitTemplateMap = Maps.newConcurrentMap();

    private Splitter splitter = Splitter.on("#");

    private SerializerFactory serializerFactory = JacksonSerializerFactory.INSTANCE;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private MessageStoreService messageStoreService;

    /**
     * 池化RabbitTemplate
     * @param message
     * @return
     * @throws MessageException
     */
    public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message.getTopic());
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitTemplateMap.get(topic);
        if (rabbitTemplate != null){
            return rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getTemplate# topic: {} is not exists, create one", topic);

        RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
        newTemplate.setExchange(topic);
        newTemplate.setRoutingKey(message.getRoutingKey());
        newTemplate.setRetryTemplate(new RetryTemplate());

        //添加message序列化和反序列化以及converter对象（使用自己的converter可以在converter中添加自己额外的逻辑）
        Serializer serializer = serializerFactory.create();
        RabbitMessageConverter converter = new RabbitMessageConverter(new GenericMessageConverter(serializer));
        newTemplate.setMessageConverter(converter);

        String messageType = message.getMessageType();
        if (!MessageType.RAPID.equals(messageType)){
            newTemplate.setConfirmCallback(this);
        }

        rabbitTemplateMap.put(topic,newTemplate);
        return newTemplate;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        List<String> strings = splitter.splitToList(correlationData.getId());
        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));
        String messageType = strings.get(2);
        if(ack) {
//            	当Broker 返回ACK成功时, 就是更新一下日志表里对应的消息发送状态为 SEND_OK
//
//             	如果当前消息类型为reliant 我们就去数据库查找并进行更新
            if(MessageType.RELIANT.endsWith(messageType)) {
                this.messageStoreService.succuess(messageId);
            }
            log.info("send message is OK, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        } else {
            log.error("send message is Fail, confirm messageId: {}, sendTime: {}", messageId, sendTime);
            //ToDo:判断当消息类型为reliant的时候，消息投递失败时需要做的可靠性保证(判断失败的原因：1.容量满了-放弃任务2.服务匆忙-投递重试)
            //todo:由es-job来定时执行从数据库中查询为未成功的消息投递任务
        }
    }
}
