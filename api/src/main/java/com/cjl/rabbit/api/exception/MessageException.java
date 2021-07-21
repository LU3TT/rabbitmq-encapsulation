package com.cjl.rabbit.api.exception;

/**
 * @Author: Be_Young
 * @Date: 2021/4/28 13:07
 */
public class MessageException extends Exception {

    private static final long serialVersionUID = -122084309737424522L;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

}
