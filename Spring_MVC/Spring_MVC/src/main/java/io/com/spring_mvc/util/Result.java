package io.com.spring_mvc.util;

import lombok.Data;
import lombok.Getter;

@Data
public class Result <T> {

    private int code;
    private String message;
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static<T> Result<T> success(T data) {
        return new Result<T>(200, "success", data);
    }

    public static<T> Result<T> success() {
        return new Result<T>(200, "success", null);
    }

    public static<T> Result<T> error(int code, String message) {
        return new Result<T>(code, message, null);
    }


}
