package com.zqtravel.music.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<Map<String, Object>> handleValidationException(Exception ex, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 40001);
        result.put("message", "参数验证失败");
        result.put("path", request.getRequestURI());
        result.put("timestamp", System.currentTimeMillis());
        
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validEx = (MethodArgumentNotValidException) ex;
            validEx.getBindingResult().getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage()));
        } else if (ex instanceof BindException) {
            BindException bindEx = (BindException) ex;
            bindEx.getFieldErrors().forEach(error -> 
                errors.put(error.getField(), error.getDefaultMessage()));
        }
        
        result.put("errors", errors);
        log.warn("参数验证失败: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", ex.getCode());
        result.put("message", ex.getMessage());
        result.put("path", request.getRequestURI());
        result.put("timestamp", System.currentTimeMillis());
        
        log.warn("业务异常: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 50000);
        result.put("message", "系统内部错误");
        result.put("path", request.getRequestURI());
        result.put("timestamp", System.currentTimeMillis());
        
        log.error("系统异常: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}