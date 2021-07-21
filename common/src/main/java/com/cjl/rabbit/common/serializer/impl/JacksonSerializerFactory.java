package com.cjl.rabbit.common.serializer.impl;

import com.cjl.rabbit.api.Message;
import com.cjl.rabbit.common.serializer.Serializer;
import com.cjl.rabbit.common.serializer.SerializerFactory;

/**
 * @Author: Be_Young
 * @Date: 2021/4/28 17:36
 */
public class JacksonSerializerFactory implements SerializerFactory {

    public static final SerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }

}
