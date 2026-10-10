package io.com.spring_mvc.exception;


public class BusinessException extends RuntimeException{

    private final int code;

    // 默认异常
    public BusinessException (String message) {
        super(message);
        this.code = 500;
    }

    // 指定状态码异常
    public BusinessException (int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
