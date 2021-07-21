package com.cjl.rabbit.api;

import com.cjl.rabbit.api.exception.MessageRunTimeException;

import java.util.List;

/**
 * @Author: Be_Young
 * @Date: 2021/4/28 13:29
 */
public interface MessageProducer {

    /**
     * 	$send消息的发送 附带SendCallback回调执行响应的业务逻辑处理
     * @param message
     * @param sendCallback
     * @throws MessageRunTimeException
     */
    void send(Message message, SendCallback sendCallback) throws MessageRunTimeException;

    /**
     * $ 消息的发送
     * @param message
     * @throws MessageRunTimeException
     */
    void send(Message message) throws MessageRunTimeException;

    /**
     * 	$send 消息的批量发送
     * @param messages
     * @throws MessageRunTimeException
     */
    void send(List<Message> messages) throws MessageRunTimeException;

}
