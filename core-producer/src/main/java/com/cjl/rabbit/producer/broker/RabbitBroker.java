package com.cjl.rabbit.producer.broker;

import com.cjl.rabbit.api.Message;

/**
 * $RabbitBroker 具体发送不同种类型消息的接口
 * @Author: Be_Young
 * @Date: 2021/4/28 14:43
 */
public interface RabbitBroker {

    /**
     *迅速消息：不需要保障消息的可靠性, 也不需要做confirm确认
     * @param message
     */
    void rapidSend(Message message);

    /**
     * 确认消息：不需要保障消息的可靠性，但是会做消息的confirm确认
     * @param message
     */
    void confirmSend(Message message);

    /**
     *可靠性消息： 一定要保障消息的100%可靠性投递，不允许有任何消息的丢失
     * @param message
     */
    void reliantSend(Message message);

    void sendMessages();

}
