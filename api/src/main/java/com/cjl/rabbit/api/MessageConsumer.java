package com.cjl.rabbit.api;

/**
 * @Author: Be_Young
 * @Date: 2021/4/28 13:29
 */
public interface MessageConsumer {

    /**
     * 监听到消息
     * @param message
     */
    void onMessage(Message message);

}
