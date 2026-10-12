package io.com.spring_mvc.exception;

import io.com.spring_mvc.util.Result;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.info("业务异常，code={}, message={}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    // 处理其他异常
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.info("其他异常：message={}", e.getMessage());
        return Result.error(500, e.getMessage());
    }

    // 处理请求体参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleValidException(MethodArgumentNotValidException e) {

        // 获取第一个校验失败的消息
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        log.warn("请求体参数校验异常：{}", message);
        return Result.error(400, message);
    }

    // 处理请求路劲参数校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleConstraintViolation(ConstraintViolationException e) {

        // 获取异常消息
        String message = e.getConstraintViolations().iterator().next().getMessage();
        log.warn("路径参数校验异常：{}", message);
        return Result.error(400, message);
    }
}
