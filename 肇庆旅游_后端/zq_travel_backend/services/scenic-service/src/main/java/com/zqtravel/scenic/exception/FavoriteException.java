package com.zqtravel.scenic.exception;

import com.zqtravel.common.core.exception.BusinessException;

/**
 * 收藏异常
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public class FavoriteException extends BusinessException {

    public FavoriteException(String message) {
        super("40002", message);
    }

    public FavoriteException(String message, Object data) {
        super("40002", message, data);
    }

    public static FavoriteException alreadyFavorited(Long userId, Long targetId) {
        return new FavoriteException("用户已收藏该景点，用户ID: " + userId + ", 景点ID: " + targetId);
    }

    public static FavoriteException notFavorited(Long userId, Long targetId) {
        return new FavoriteException("用户未收藏该景点，用户ID: " + userId + ", 景点ID: " + targetId);
    }

    public static FavoriteException invalidAction(String action) {
        return new FavoriteException("无效的收藏操作: " + action);
    }
}