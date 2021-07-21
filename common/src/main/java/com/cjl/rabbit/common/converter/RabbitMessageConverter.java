package com.cjl.rabbit.common.converter;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * 对GenericMessageConverter的装饰（装饰器模式）
 * @Author: Be_Young
 * @Date: 2021/4/28 20:22
 */
public class RabbitMessageConverter implements MessageConverter {

    private GenericMessageConverter delegate;

    //	private final String delaultExprie = String.valueOf(24 * 60 * 60 * 1000);消息过期时间

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        //		messageProperties.setExpiration(delaultExprie);装饰器增加额外的自己的逻辑
        //      messageProperties.setContentEncoding("utf-8");
        com.cjl.rabbit.api.Message message = (com.cjl.rabbit.api.Message) object;
        messageProperties.setDelay(message.getDelayMills());
        return this.delegate.toMessage(object, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        com.cjl.rabbit.api.Message msg = (com.cjl.rabbit.api.Message) this.delegate.fromMessage(message);
        return msg;
    }
}
