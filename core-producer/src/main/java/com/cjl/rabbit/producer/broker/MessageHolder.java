package com.cjl.rabbit.producer.broker;

import com.cjl.rabbit.api.Message;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author: Be_Young
 * @Date: 2021/5/9 16:14
 */
public class MessageHolder {

    private List<Message>messages = Lists.newArrayList();

    /**
     * 为什么要重写initialValue呢?
     * 答：在ThreadLocal内有个setInitialValue方法，会调用initialValue方法，并执行跟set方法类似的逻辑。
     */
    public static final ThreadLocal<MessageHolder> holder = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return new MessageHolder();
        }
    };

    public static void add(Message message) {
        holder.get().messages.add(message);
    }

    public static List<Message> clear() {
        List<Message> tmp = Lists.newArrayList(holder.get().messages);
        holder.remove();
        return tmp;
    }

}
