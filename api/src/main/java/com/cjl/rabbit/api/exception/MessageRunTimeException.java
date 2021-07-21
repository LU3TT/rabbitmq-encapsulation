package com.cjl.rabbit.api.exception;

/**
 * @Author: Be_Young
 * @Date: 2021/4/28 13:07
 */
public class MessageRunTimeException extends RuntimeException {

    private static final long serialVersionUID = -4101721930926869297L;

    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }

}
