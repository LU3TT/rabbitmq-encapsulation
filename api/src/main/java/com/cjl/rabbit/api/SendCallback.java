package com.cjl.rabbit.api;

/**
 * @Author: Be_Young
 * @Date: 2021/4/28 13:29
 */
public interface SendCallback {

    /**
     * 成功回调
     */
    void onSuccess();

    /**
     * 失败回调
     */
    void onFailure();

}
