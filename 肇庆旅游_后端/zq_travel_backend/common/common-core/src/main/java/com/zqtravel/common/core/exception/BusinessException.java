package com.zqtravel.common.core.exception;

import lombok.Getter;

import java.io.Serializable;

/**
 * 业务异常基类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-05
 */
@Getter
public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 错误数据
     */
    private final Object data;

    /**
     * 构造业务异常
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = null;
    }

    /**
     * 构造业务异常
     *
     * @param code    错误码
     * @param message 错误消息
     * @param cause   原因异常
     */
    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.data = null;
    }

    /**
     * 构造业务异常（带数据）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param data    错误数据
     */
    public BusinessException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构造业务异常（带数据和原因）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param data    错误数据
     * @param cause   原因异常
     */
    public BusinessException(String code, String message, Object data, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 参数验证异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException validationError(String message) {
        return new BusinessException("40001", message);
    }

    /**
     * 参数验证异常（带数据）
     *
     * @param message 错误消息
     * @param data    错误数据
     * @return 业务异常
     */
    public static BusinessException validationError(String message, Object data) {
        return new BusinessException("40001", message, data);
    }

    /**
     * 业务逻辑异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException businessError(String message) {
        return new BusinessException("40002", message);
    }

    /**
     * 业务逻辑异常（带数据）
     *
     * @param message 错误消息
     * @param data    错误数据
     * @return 业务异常
     */
    public static BusinessException businessError(String message, Object data) {
        return new BusinessException("40002", message, data);
    }

    /**
     * 未授权异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException unauthorizedError(String message) {
        return new BusinessException("401", message);
    }

    /**
     * 禁止访问异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException forbiddenError(String message) {
        return new BusinessException("403", message);
    }

    /**
     * 资源不存在异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException notFoundError(String message) {
        return new BusinessException("404", message);
    }

    /**
     * 资源不存在异常（带数据）
     *
     * @param message 错误消息
     * @param data    错误数据
     * @return 业务异常
     */
    public static BusinessException notFoundError(String message, Object data) {
        return new BusinessException("404", message, data);
    }

    /**
     * 服务器内部错误异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException internalError(String message) {
        return new BusinessException("500", message);
    }

    /**
     * 服务器内部错误异常（带原因）
     *
     * @param message 错误消息
     * @param cause   原因异常
     * @return 业务异常
     */
    public static BusinessException internalError(String message, Throwable cause) {
        return new BusinessException("500", message, cause);
    }

    /**
     * 数据库操作异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException databaseError(String message) {
        return new BusinessException("50001", message);
    }

    /**
     * 数据库操作异常（带原因）
     *
     * @param message 错误消息
     * @param cause   原因异常
     * @return 业务异常
     */
    public static BusinessException databaseError(String message, Throwable cause) {
        return new BusinessException("50001", message, cause);
    }

    /**
     * 外部服务调用异常
     *
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException externalServiceError(String message) {
        return new BusinessException("50002", message);
    }

    /**
     * 外部服务调用异常（带原因）
     *
     * @param message 错误消息
     * @param cause   原因异常
     * @return 业务异常
     */
    public static BusinessException externalServiceError(String message, Throwable cause) {
        return new BusinessException("50002", message, cause);
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}