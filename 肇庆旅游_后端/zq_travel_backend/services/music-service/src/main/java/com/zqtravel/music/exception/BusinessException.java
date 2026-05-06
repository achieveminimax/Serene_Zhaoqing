package com.zqtravel.music.exception;

import lombok.Getter;

/**
 * 业务异常
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String code;
    private final String message;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}