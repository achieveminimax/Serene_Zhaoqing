package com.zqtravel.scenic.exception;

import com.zqtravel.common.core.exception.BusinessException;

/**
 * 景点不存在异常
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public class ScenicNotFoundException extends BusinessException {

    public ScenicNotFoundException(Long scenicId) {
        super("404", "景点不存在，ID: " + scenicId);
    }

    public ScenicNotFoundException(String message) {
        super("404", message);
    }
}