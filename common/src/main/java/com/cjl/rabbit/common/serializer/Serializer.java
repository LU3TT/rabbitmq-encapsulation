package com.cjl.rabbit.common.serializer;

/**
 * @Author: Be_Young
 * @Date: 2021/4/28 17:26
 */
public interface Serializer {

    /**
     * 序列化为字节
     * @param data
     * @return
     */
    byte[] serializeRaw(Object data);

    /**
     * 序列化为JSON字符串
     * @param data
     * @return
     */
    String serialize(Object data);

    /**
     * JSON字符串反序列化为对象
     * @param content
     * @param <T>
     * @return
     */
    <T> T deserialize(String content);

    /**
     * 字节码转换为对象
     * @param content
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] content);

}
