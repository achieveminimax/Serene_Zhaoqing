package com.zqtravel.common.web.exception;

import com.zqtravel.common.core.exception.BusinessException;
import com.zqtravel.common.core.model.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    @DisplayName("处理BusinessException应返回对应HTTP状态码")
    void handleBusinessException_ShouldReturnCorrectStatus() {
        BusinessException e = BusinessException.unauthorizedError("未授权");
        ResponseEntity<ApiResponse<Object>> response = handler.handleBusinessException(e);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("401", response.getBody().getCode());
        assertEquals("未授权", response.getBody().getMessage());
    }

    @Test
    @DisplayName("处理参数验证异常应返回400状态码")
    void handleMethodArgumentNotValidException_ShouldReturn400() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "object");
        bindingResult.addError(new FieldError("object", "name", "不能为空"));

        org.springframework.web.bind.MethodArgumentNotValidException e =
                new org.springframework.web.bind.MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ApiResponse<Object>> response = handler.handleMethodArgumentNotValidException(e);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("40001", response.getBody().getCode());
    }

    @Test
    @DisplayName("处理未知异常应返回500状态码")
    void handleException_ShouldReturn500() {
        Exception e = new RuntimeException("未知错误");

        ResponseEntity<ApiResponse<Object>> response = handler.handleException(e);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("500", response.getBody().getCode());
        assertEquals("服务器内部错误", response.getBody().getMessage());
    }

    @Test
    @DisplayName("处理404业务异常应返回NOT_FOUND状态码")
    void handleNotFoundException_ShouldReturn404() {
        BusinessException e = BusinessException.notFoundError("资源不存在");
        ResponseEntity<ApiResponse<Object>> response = handler.handleBusinessException(e);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("404", response.getBody().getCode());
    }

    @Test
    @DisplayName("处理BindException应返回400状态码")
    void handleBindException_ShouldReturn400() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "object");
        bindingResult.addError(new FieldError("object", "email", "邮箱格式不正确"));

        org.springframework.validation.BindException e =
                new org.springframework.validation.BindException(bindingResult);

        ResponseEntity<ApiResponse<Object>> response = handler.handleBindException(e);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("40001", response.getBody().getCode());
    }
}
