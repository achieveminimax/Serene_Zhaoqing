package com.zqtravel.common.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一API响应格式
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应状态码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应时间戳
     */
    private Long timestamp;

    /**
     * 成功响应（无数据）
     *
     * @return 成功响应
     */
    public static ApiResponse<Object> success() {
        return ApiResponse.builder()
                .code("200")
                .message("success")
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 成功响应（有数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 成功响应
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code("200")
                .message("success")
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 成功响应（自定义消息）
     *
     * @param message 自定义消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return 成功响应
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code("200")
                .message(message)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 错误响应
     *
     * @param code    错误码
     * @param message 错误消息
     * @return 错误响应
     */
    public static ApiResponse<Object> error(String code, String message) {
        return ApiResponse.builder()
                .code(code)
                .message(message)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 错误响应（带数据）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param data    错误数据
     * @param <T>     数据类型
     * @return 错误响应
     */
    public static <T> ApiResponse<T> error(String code, String message, T data) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 参数验证失败响应
     *
     * @param message 错误消息
     * @return 错误响应
     */
    public static ApiResponse<Object> validationError(String message) {
        return error("40001", message);
    }

    /**
     * 业务异常响应
     *
     * @param message 错误消息
     * @return 错误响应
     */
    public static ApiResponse<Object> businessError(String message) {
        return error("40002", message);
    }

    /**
     * 未授权响应
     *
     * @param message 错误消息
     * @return 错误响应
     */
    public static ApiResponse<Object> unauthorizedError(String message) {
        return error("401", message);
    }

    /**
     * 禁止访问响应
     *
     * @param message 错误消息
     * @return 错误响应
     */
    public static ApiResponse<Object> forbiddenError(String message) {
        return error("403", message);
    }

    /**
     * 资源不存在响应
     *
     * @param message 错误消息
     * @return 错误响应
     */
    public static ApiResponse<Object> notFoundError(String message) {
        return error("404", message);
    }

    /**
     * 服务器内部错误响应
     *
     * @param message 错误消息
     * @return 错误响应
     */
    public static ApiResponse<Object> internalError(String message) {
        return error("500", message);
    }

    /**
     * 判断响应是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return "200".equals(this.code);
    }

    /**
     * 判断响应是否失败
     *
     * @return 是否失败
     */
    public boolean isError() {
        return !isSuccess();
    }
}